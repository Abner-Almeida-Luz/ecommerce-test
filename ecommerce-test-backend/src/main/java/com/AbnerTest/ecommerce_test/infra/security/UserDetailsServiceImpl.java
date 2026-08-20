package com.AbnerTest.ecommerce_test.infra.security;

import com.AbnerTest.ecommerce_test.elements.Users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByActiveTrueAndLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + username));
    }
}
