package com.app.streamify.controller;

import com.app.streamify.dto.AuthResponse;
import com.app.streamify.dto.LoginData;
import com.app.streamify.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginData request){
        System.out.println(request);
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
