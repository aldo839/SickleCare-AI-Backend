package com.sicklecare.api.controllers;

import com.sicklecare.api.dtos.PatientRegistrationDTO;
import com.sicklecare.api.dtos.PatientResponseDTO;
import com.sicklecare.api.dtos.PatientUpdateDTO;
import com.sicklecare.api.repository.PatientRepository;
import com.sicklecare.api.services.PatientService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {

        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {

        return ResponseEntity.ok(patientService.getPatientByID(id));
    }

    @PostMapping("/register")
    public ResponseEntity<PatientResponseDTO> registerPatient(@Valid @RequestBody PatientRegistrationDTO patientRegistrationDTO) throws MessagingException, UnsupportedEncodingException {

        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.registerPatient(patientRegistrationDTO));
    }

    @PostMapping("/activation")
    public ResponseEntity<String> activation(@RequestBody Map<String, String> activation) {

        patientService.activatePatient(activation);
        return ResponseEntity.ok("Your account is enable !");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientUpdateDTO patientUpdateDTO){

        return ResponseEntity.ok(patientService.updatePatient(id, patientUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id){

        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient deleted Successfully");
    }

}
