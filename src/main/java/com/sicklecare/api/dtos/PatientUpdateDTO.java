package com.sicklecare.api.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatientUpdateDTO {

    @Size(min = 4, max = 20, message = "Username will have between 4 and 20 characters !")
    private String username;
    @Email(message = "Email will be valid !")
    private String email;
    @Size(min = 8, message = "Password will have 8 or more characters !")
    private String password;
    @Positive(message = "Weight should be positive !")
    private Double weight;
    private String region;
    private String city;

}
