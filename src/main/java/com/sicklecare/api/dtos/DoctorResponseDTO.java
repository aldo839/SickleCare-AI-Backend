package com.sicklecare.api.dtos;

import com.sicklecare.api.models.Role;

public record DoctorResponseDTO(

        Long id,
        String username,
        String email,
        String name,
        Role role,
        String speciality,
        String matricule,
        String validationLetterReference,
        String hospitalUnit,
        String region,
        String city,
        String hospital

) {
}
