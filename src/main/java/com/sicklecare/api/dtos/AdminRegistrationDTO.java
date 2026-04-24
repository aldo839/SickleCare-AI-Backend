package com.sicklecare.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegistrationDTO {

    @NotBlank(message = "Username is required !")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters !")
    private String userName;
    @Email(message = "Email should be valid !")
    @NotBlank(message = "Email is required !")
    private String email;
    @NotBlank(message = "Password is required !")
    @Size(min = 5, max = 20, message = "Password must be between 8 and 20 characters !")
    private String password;
    @NotBlank(message = "Firstname is required!")
    private String firstname;
    @NotBlank(message = "Lastname is required!")
    private String lastname;

}
