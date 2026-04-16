package com.sicklecare.api.repository;

import com.sicklecare.api.models.PatientValidation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientValidationRepository extends JpaRepository<PatientValidation, Long> {
}
