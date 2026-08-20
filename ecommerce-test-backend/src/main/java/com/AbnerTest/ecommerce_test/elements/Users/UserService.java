package com.AbnerTest.ecommerce_test.elements.Users;

import com.AbnerTest.ecommerce_test.core.Carts;
import com.AbnerTest.ecommerce_test.core.Users;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions.ResourceNotFoundException;
import com.AbnerTest.ecommerce_test.infra.security.TokenService;
import com.AbnerTest.ecommerce_test.elements.Carts.CartsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CartsRepository cartsRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse register(RegisterRequest request) {
        log.info("Registring User={}", request);
        if (userRepository.findByActiveTrueAndLogin(request.login()).isPresent()) {
            throw new Exceptions.DuplicateLoginException("Login already in use");
        }
        String encryptedPassword = passwordEncoder.encode(request.password());
        Users newUser = userRepository.save(new Users(request.username(),request.login(),encryptedPassword,request.role()));
        cartsRepository.save(new Carts(newUser));
        log.info("Registring completed. newUser={}", newUser);
        return userMapper.toDTO(newUser);
    }

    public LoginResponse login(LoginRequest request) {
        log.info("Login loginRequest={}", request);
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var acessToken = tokenService.generateToken((Users) Objects.requireNonNull(auth.getPrincipal()));
        var refreshToken = tokenService.generateRefreshToken((Users) Objects.requireNonNull(auth.getPrincipal()));
        log.info("Login completed. loginRequest={} acessToken={}, refreshToken={} ", request,acessToken,refreshToken);
        return new LoginResponse(acessToken, refreshToken);
    }

    public LoginResponse refresh(String refreshToken) {
        String login = tokenService.validateRefreshToken(refreshToken);
        Users user = userRepository.findByActiveTrueAndLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with login: " + login));

        String newAccessToken = tokenService.generateToken(user);
        return new LoginResponse(newAccessToken, refreshToken);
    }

    public UserResponse findByLogin(String login) {
        log.info("findByLogin login={}", login);
        Users user = userRepository.findByActiveTrueAndLogin(login).orElseThrow(() -> new ResourceNotFoundException("User not found with login " + login));
        return userMapper.toDTO(user);
    }

    public List<UserResponse> listAll(){
        log.info("Listing all users");
        return userRepository.findAllByActiveTrue().stream().map(userMapper::toDTO).toList();
    }

    public void deleteById(Long id) {
        log.info("Deleting User={}", id);
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setActive(false);
        user.setDeletedAt(LocalDateTime.now());
        log.info("Deleting completed. User={}", id);
        userRepository.save(user);
    }
}
