package com.sicklecare.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DoctorUpdateDTO {

    @Size(min = 4, max = 20, message = "Username will have between 4 and 20 characters !")
    private String username;
    @Email(message = "Email will be valid !")
    private String email;
    private String password;
    private String hospitalUnit;
    private String region;
    private String city;
    private Double hospital;

}
