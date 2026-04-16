package com.sicklecare.api.repository;

import com.sicklecare.api.models.PatientValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientValidationRepository extends JpaRepository<PatientValidation, Long> {

    Optional<PatientValidation> findByCode(String code);
}
