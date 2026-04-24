package com.sicklecare.api.controllers;

import com.sicklecare.api.dtos.*;
import com.sicklecare.api.services.AuthService;
import com.sicklecare.api.services.DoctorService;
import com.sicklecare.api.services.PatientService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO){

        return ResponseEntity.ok(authService.authenticate(loginDTO));
    }

    @PostMapping("/register/patient")
    public ResponseEntity<PatientResponseDTO> registerPatient(@Valid @RequestBody PatientRegistrationDTO patientRegistrationDTO) throws MessagingException, UnsupportedEncodingException {

        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.registerPatient(patientRegistrationDTO));
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<DoctorResponseDTO> registerDoctor(@Valid @RequestBody DoctorRegistrationDTO doctorRegistrationDTO) throws MessagingException, UnsupportedEncodingException {

        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.registerDoctor(doctorRegistrationDTO));
    }

}
