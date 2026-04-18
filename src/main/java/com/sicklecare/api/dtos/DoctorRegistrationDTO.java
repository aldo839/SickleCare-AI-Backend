package com.sicklecare.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DoctorRegistrationDTO {

    @NotBlank(message = "Username is require !")
    @Size(min = 4, max = 20, message = "Username will have between 4 and 20 caraters !")
    private String username;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email address is require !")
    private String email;
    @NotBlank(message = "Password is require !")
    @Size(min = 8, message = "Password will have 8 caraters or more !")
    private String password;
    @NotBlank(message = "First name is require !")
    private String firstname;
    @NotBlank(message = "First name is require !")
    private String lastname;
    private String speciality;
    private String matricule;
    private String validationLetterReference;
    private String hospitalUnit;
    private String region;
    private String city;
    private String hospital;

}
