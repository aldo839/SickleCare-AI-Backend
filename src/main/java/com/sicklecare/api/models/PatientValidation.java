package com.sicklecare.api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "validation")
public class PatientValidation {

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
