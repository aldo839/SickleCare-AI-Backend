package com.sicklecare.api.repository;

import com.sicklecare.api.models.Patient;
import com.sicklecare.api.models.PatientSex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findBySex(PatientSex sex);

    List<Patient> findByRegion(String region);

    List<Patient> findByCity(String city);

    List<Patient> findByValidatedTrue();

    List<Patient> findByGenotype(String genotype);

    List<Patient> findByDoctorId(Long doctorId);

    Boolean existsByIdAndDoctorId(Long patientId, Long doctorId);

}
