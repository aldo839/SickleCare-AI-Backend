package com.sicklecare.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter @Setter
public abstract class Validation {

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
