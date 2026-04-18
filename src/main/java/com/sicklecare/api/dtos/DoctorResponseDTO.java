package com.sicklecare.api.dtos;

public record DoctorResponseDTO(

        Long id,
        String username,
        String email,
        String role,
        String speciality,
        String matricule,
        String validationLetterReference,
        String hospitalUnit,
        String region,
        String city,
        String hospital

) {
}
