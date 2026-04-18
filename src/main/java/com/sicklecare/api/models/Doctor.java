package com.sicklecare.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "doctors")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Doctor extends User {

    private String firstname;
    private String lastname;
    private String speciality;
    private String matricule;
    private String validationLetterReference;
    private String hospitalUnit;
    private String region;
    private String city;
    private String hospital;

}
