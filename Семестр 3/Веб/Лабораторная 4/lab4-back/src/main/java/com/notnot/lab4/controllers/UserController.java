package com.notnot.lab4.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.notnot.lab4.dto.AuthRequest;
import com.notnot.lab4.dto.AuthResponse;
import com.notnot.lab4.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        try {
            String token = userService.register(request).getToken();
            return ResponseEntity
                    .ok()
                    .header("Authorization", token)
                    .build();
        } catch (ResponseStatusException e) {
            AuthResponse response = new AuthResponse(null, e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            String token = userService.login(request).getToken();

            return ResponseEntity
                    .ok()
                    .header("Authorization", token)
                    .build();
        } catch (ResponseStatusException e) {
            AuthResponse response = new AuthResponse(null, e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(response);
        }
    }
}
