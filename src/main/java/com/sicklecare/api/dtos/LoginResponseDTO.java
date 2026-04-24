package com.sicklecare.api.dtos;

public record LoginResponseDTO(

        String token,
        String username,
        String role,
        String firstname,
        String lastname

) {
}
