package com.sicklecare.api.repository;
import com.sicklecare.api.models.DoctorValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorValidationRepository extends JpaRepository<DoctorValidation, Long> {

    Optional<DoctorValidation> findByCode(String code);
}
