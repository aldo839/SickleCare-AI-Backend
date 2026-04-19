package com.sicklecare.api.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "patient_validation")
public class PatientValidation extends Validation{

}
