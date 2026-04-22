package com.sicklecare.api.dtos;

import com.sicklecare.api.models.Role;

public record AdminResponseDTO(

        Long id,
        String username,
        String email,
        String firstname,
        String lastname,
        Role role

) {
}
