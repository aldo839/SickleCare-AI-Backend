package com.sicklecare.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Patient extends User {

    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    private PatientSex sex;
    @Column(name = "birth_date")
    @Past
    private LocalDate birthDate;
    @Column(name = "blood_type")
    private String bloodType;
    private String genotype;
    private Double weight;
    private String region;
    private String city;

}