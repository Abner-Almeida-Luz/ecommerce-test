package com.AbnerTest.ecommerce_test.elements.Users;

import com.AbnerTest.ecommerce_test.core.Carts;
import com.AbnerTest.ecommerce_test.core.UserRole;
import com.AbnerTest.ecommerce_test.core.Users;
import com.AbnerTest.ecommerce_test.elements.Carts.CartsRepository;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions;
import com.AbnerTest.ecommerce_test.infra.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Transactional
@ActiveProfiles("test")
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock UserRepository userRepository;
    @Mock CartsRepository cartsRepository;
    @Mock TokenService tokenService;
    @Mock PasswordEncoder passwordEncoder;
    @Mock AuthenticationManager authenticationManager;
    @Mock UserMapper userMapper;

    @InjectMocks UserService userService;

    @Test
    @DisplayName("Register user and return User Response")
    void register_whenUserDoesntExists_returnUserResponse(){
        Users user = new Users("joao","joao@gmail.com","senha123",UserRole.ADMIN);
        UserResponse expected = new UserResponse(user.getUserId(),user.getUsername(),user.getLogin(),user.getRole(),user.getCreatedAt());
        RegisterRequest request = new RegisterRequest(user.getUsername(),user.getLogin(),"senha123",UserRole.ADMIN);

        when(userRepository.findByActiveTrueAndLogin(user.getLogin())).thenReturn(Optional.empty());
        when(userRepository.save(any(Users.class))).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(expected);

        UserResponse result = userService.register(request);

        assertThat(result).isEqualTo(expected);
        verify(userRepository).findByActiveTrueAndLogin(user.getLogin());
        verify(cartsRepository).save(any(Carts.class));
    }

    @Test
    @DisplayName("Return exception if login is already in use")
    void register_whenUserAlreadyExists_thrownException() {
        Users user = new Users("joao","joao@gmail.com","senha123",UserRole.ADMIN);
        RegisterRequest request = new RegisterRequest(user.getUsername(),user.getLogin(),"senha123",UserRole.ADMIN);

        when(userRepository.findByActiveTrueAndLogin("joao@gmail.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.register(request)).isInstanceOf(Exceptions.DuplicateLoginException.class).hasMessageContaining("already in use");
    }

    @Test
    @DisplayName("Login user and return User Response")
    void login_whenValidCredential_returnUserResponse(){
        Users user = new Users("joao","joao@gmail.com","senha123",UserRole.ADMIN);
        LoginRequest request = new LoginRequest(user.getLogin(),"senha123");
        Authentication auth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(tokenService.generateToken(user)).thenReturn("fake-jwt-token");
        when(tokenService.generateRefreshToken(user)).thenReturn("fake-refresh-jwt-token");

        LoginResponse result = userService.login(request);

        assertThat(result).isEqualTo(new LoginResponse("fake-jwt-token","fake-refresh-jwt-token"));
        verify(tokenService).generateToken(user);
    }

    @Test
    @DisplayName("Return exception if invalid credential")
    void login_whenInvalidCredential_throwException(){
        LoginRequest request = new LoginRequest("ghost@gmail.com","wrongPassword");

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid login or password"));

        assertThatThrownBy(() -> userService.login(request)).isInstanceOf(BadCredentialsException.class);
    }

    @Test
    @DisplayName("Return user response if exists user")
    void findUserByLogin_whenExists_returnUserResponse(){
        Users user = new Users("joao","joao@gmail.com","senha123",UserRole.ADMIN);
        UserResponse expected = new UserResponse(user.getUserId(),user.getUsername(),user.getLogin(),user.getRole(),user.getCreatedAt());

        when(userRepository.findByActiveTrueAndLogin(user.getLogin())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(expected);

        UserResponse result = userService.findByLogin(user.getLogin());

        assertThat(result).isEqualTo(expected);
        verify(userRepository).findByActiveTrueAndLogin(user.getLogin());
    }

    @Test
    @DisplayName("Return exception if user doesnt exists")
    void findUserByLogin_whenDoesntExists_throwException(){
        when(userRepository.findByActiveTrueAndLogin("ghost@gmail.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findByLogin("ghost@gmail.com")).isInstanceOf(Exceptions.ResourceNotFoundException.class).hasMessageContaining("not found");
    }
}
