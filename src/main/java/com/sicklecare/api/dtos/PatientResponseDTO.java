package com.sicklecare.api.dtos;

import java.time.LocalDate;

public record PatientResponseDTO(

        Long id,
        String username,
        String email,
        String role,
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
