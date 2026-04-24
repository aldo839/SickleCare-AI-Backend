package com.sicklecare.api.controllers;

import com.sicklecare.api.dtos.*;
import com.sicklecare.api.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN')")
    public ResponseEntity<List<AdminResponseDTO>> getAllAdmin() {
        return ResponseEntity.ok(adminService.getAllAdmin());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN')")
    public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminByID(id));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROOT')")
    public ResponseEntity<AdminResponseDTO> registerAdmin(@Valid @RequestBody AdminRegistrationDTO adminRegistrationDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(adminRegistrationDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROOT')")
    public ResponseEntity<AdminResponseDTO> updateAdmin(@PathVariable Long id, @Valid @RequestBody AdminUpdateDTO adminUpdateDTO){
        return ResponseEntity.ok(adminService.updateAdmin(id, adminUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROOT')")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }
}