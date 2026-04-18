package com.sicklecare.api.dtos;

import com.sicklecare.api.models.Role;

public record PatientResponseDTO(

        Long id,
        String username,
        String email,
        Role role,
        String name,
        Boolean sex,
        Integer age,
        String genotype,
        String bloodType,
        Double weight,
        String region,
        String city

) {
}
