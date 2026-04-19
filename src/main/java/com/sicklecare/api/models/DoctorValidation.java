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
public class DoctorValidation extends Validation {

}
