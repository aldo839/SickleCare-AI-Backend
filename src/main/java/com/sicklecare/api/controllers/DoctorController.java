package com.sicklecare.api.controllers;

import com.sicklecare.api.dtos.DoctorRegistrationDTO;
import com.sicklecare.api.dtos.DoctorResponseDTO;
import com.sicklecare.api.dtos.DoctorUpdateDTO;
import com.sicklecare.api.services.DoctorService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin("*")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/all")
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {

        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable Long id) {

        return ResponseEntity.ok(doctorService.getDoctorByID(id));
    }

    @PostMapping("/register")
    public ResponseEntity<DoctorResponseDTO> registerDoctor(@Valid @RequestBody DoctorRegistrationDTO doctorRegistrationDTO) throws MessagingException, UnsupportedEncodingException {

        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.registerDoctor(doctorRegistrationDTO));
    }

    @PostMapping("/activation")
    public ResponseEntity<Map<String, String>> activation(@RequestBody Map<String, String> activation) {

        String token = doctorService.activateDoctor(activation);

        Map<String, String> response = Map.of(
                "message", "Your account is enabled !",
                "token", token
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorUpdateDTO doctorUpdateDTO){

        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id){

        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted Successfully");
    }

}
