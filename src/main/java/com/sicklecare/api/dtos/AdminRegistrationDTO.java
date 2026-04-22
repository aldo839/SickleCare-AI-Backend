package com.sicklecare.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegistrationDTO {

    @NotBlank(message = "Username is require !")
    @Size(min = 5, max = 20, message = "Username may have between 5 and 20 characters !")
    private String userName;
    @Email(message = "Email should be valid !")
    @NotBlank(message = "Email is require !")
    private String email;
    @NotBlank(message = "Password is require !")
    @Size(min = 5, max = 20, message = "Username may have between 8 and 20 characters !")
    private String password;
    private String firstname;
    private String lastname;

}
