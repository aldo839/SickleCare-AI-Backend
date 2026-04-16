package com.sicklecare.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequestDTO {

    @NotBlank(message = "Email is require !")
    @Email(message = "Email should be valid !")
    private String email;

    @NotBlank(message = "Password is require !")
    private String password;

}
