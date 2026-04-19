package com.sicklecare.api.controllers;

import com.sicklecare.api.config.JwtUtils;
import com.sicklecare.api.dtos.LoginRequestDTO;
import com.sicklecare.api.repository.UserRepository;
import com.sicklecare.api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDTO loginDTO){

        return ResponseEntity.ok(authService.authenticate(loginDTO));
    }

}
