package com.sicklecare.api.dtos;

public record MfaVerificationRequestDTO(

        String email,
        String code

) {
}
