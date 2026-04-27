package com.sicklecare.api.controllers;

import com.sicklecare.api.dtos.DoctorResponseDTO;
import com.sicklecare.api.dtos.DoctorUpdateDTO;
import com.sicklecare.api.services.DoctorService;
import com.sicklecare.api.services.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin("*")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final PatientService patientService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {

       return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable Long id) {

        return ResponseEntity.ok(doctorService.getDoctorByID(id));
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
    @PreAuthorize("hasAuthority('DOCTOR') and #id == principal.id")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorUpdateDTO doctorUpdateDTO){

        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id){

        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted Successfully");
    }

    @PatchMapping("/validate-doctor/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    public ResponseEntity<Map<String, String>> validateDoctor(@PathVariable Long id){

        doctorService.validateDoctor(id);

        return ResponseEntity.ok(Map.of("message", "Doctor validated successfully !"));
    }

}
