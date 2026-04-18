package com.sicklecare.api.dtos;

import com.sicklecare.api.models.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class PatientRegistrationDTO {

    @NotBlank(message = "Username is require !")
    @Size(min = 4, max = 20, message = "Username will have between 4 and 20 characters !")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is require !")
    private String email;

    @NotBlank(message = "Password is require !")
    @Size(min = 8, message = "Password will have 8 characters or more !")
    private String password;

    private String firstname;
    private String lastname;
    private Boolean sex;

    @Past(message = "Birth date should be past !")
    private LocalDate birthDate;
    private String genotype;
    private String bloodType;

    @Positive(message = "Weight should be positive !")
    private Double weight;
    private String region;
    private String city;

}
