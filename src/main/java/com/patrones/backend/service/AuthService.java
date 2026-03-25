package com.patrones.backend.service;

import com.patrones.backend.dto.AuthResponse;
import com.patrones.backend.dto.LoginRequest;
import com.patrones.backend.dto.RegisterRequest;
import com.patrones.backend.model.Role;
import com.patrones.backend.model.User;
import com.patrones.backend.repository.UserRepository;
import com.patrones.backend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already taken");
        }

        User user = new User(
                null,
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName(),
                Role.USER,
                LocalDateTime.now()
        );
        
        userRepository.save(user);
        
        var userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
        
        String jwtToken = jwtService.generateToken(userDetails);
        
        return new AuthResponse(
                jwtToken,
                user.getId(),
                user.getEmail(),
                user.getFullName()
        );
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        var userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
        
        String jwtToken = jwtService.generateToken(userDetails);
        
        return new AuthResponse(
                jwtToken,
                user.getId(),
                user.getEmail(),
                user.getFullName()
        );
    }
}
