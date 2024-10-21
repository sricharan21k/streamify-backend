package com.app.streamify.service;

import com.app.streamify.dto.AuthResponse;
import com.app.streamify.dto.LoginData;
import com.app.streamify.model.User;
import com.app.streamify.repository.UserRepository;
import com.app.streamify.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse authenticate(LoginData request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword()));

        return AuthResponse.builder().authToken(jwtService.generateToken(user)).username(user.getUsername()).build();
    }
}
