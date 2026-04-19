package com.sicklecare.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "doctor_validation")
public class DoctorValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant creation;
    private Instant expiration;
    private Instant validation;
    private String code;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

}
