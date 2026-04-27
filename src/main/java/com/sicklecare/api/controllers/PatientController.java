package com.sicklecare.api.controllers;

import com.sicklecare.api.dtos.PatientResponseDTO;
import com.sicklecare.api.dtos.PatientUpdateDTO;
import com.sicklecare.api.models.User;
import com.sicklecare.api.services.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {

        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'DOCTOR') or (hasAuthority('PATIENT') and #id == principal.id)")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {

        return ResponseEntity.ok(patientService.getPatientByID(id));
    }

    @PostMapping("/activation")
    public ResponseEntity<Map<String, String>> activation(@RequestBody Map<String, String> activation) {

        String token = patientService.activatePatient(activation);

        Map<String, String> response = Map.of(
                "message", "Your account is enabled !",
                "token", token
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT') and #id == principal.id")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientUpdateDTO patientUpdateDTO){

        return ResponseEntity.ok(patientService.updatePatient(id, patientUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROOT')")
    public ResponseEntity<String> deletePatient(@PathVariable Long id){

        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient deleted Successfully");
    }

    @GetMapping("/my-patients")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<List<PatientResponseDTO>> getMyPatients(@AuthenticationPrincipal User connectedUser) {

        Long doctorId = connectedUser.getId();
        return ResponseEntity.ok(patientService.getPatientsByDoctor(doctorId));
    }

}
