package com.sicklecare.api.repository;

import com.sicklecare.api.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpeciality(String speciality);

    List<Doctor> findByRegion(String region);

    List<Doctor> findByCity(String city);

    List<Doctor> findByHospital(String hospital);
}
