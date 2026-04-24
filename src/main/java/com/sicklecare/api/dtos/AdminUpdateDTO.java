package com.sicklecare.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateDTO {

    @Size(min = 4, max = 20, message = "Username will have between 5 and 20 characters !")
    private String username;
    @Email(message = "Email will be valid !")
    private String email;
    @Size(min = 8, message = "Password must have 8 characters or more !")
    private String password;

}
