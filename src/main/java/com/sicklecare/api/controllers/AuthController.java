package com.sicklecare.api.controllers;

import com.sicklecare.api.dtos.*;
import com.sicklecare.api.exceptions.BadCredentialsException;
import com.sicklecare.api.services.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final OAuth2Service oAuth2Service;
    private final LogoutService logoutService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO){

        return ResponseEntity.ok(authService.authenticate(loginDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {

        try {
            logoutService.invalidateToken(authHeader);
            SecurityContextHolder.clearContext();

            return ResponseEntity.ok("Logout Successfully.");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during logout process.");

        }
    }

    @PostMapping("/register/patient")
    public ResponseEntity<PatientResponseDTO> registerPatient(@Valid @RequestBody PatientRegistrationDTO patientRegistrationDTO) throws MessagingException, UnsupportedEncodingException {

        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.registerPatient(patientRegistrationDTO));
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<DoctorResponseDTO> registerDoctor(@Valid @RequestBody DoctorRegistrationDTO doctorRegistrationDTO) throws MessagingException, UnsupportedEncodingException {

        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.registerDoctor(doctorRegistrationDTO));
    }

    // Implementation of OAuth
    @GetMapping("/oauth2-success")
    public ResponseEntity<?> handleOAuth2Success (Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {

            Object principal = authentication.getPrincipal();

            if (principal instanceof OAuth2User oAuth2User) {
                String token = oAuth2Service.processOauth2User(oAuth2User);

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                throw new BadCredentialsException("Unsupported authentication type");
            }

        } catch (BadCredentialsException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Access Denial");
            error.put("message", "Authentication error. Please verify your access !");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }
    }

}
