package com.sicklecare.api.repository;

import com.sicklecare.api.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findBySex(Boolean sex);

    List<Patient> findByRegion(String region);

    List<Patient> findByCity(String city);

}
