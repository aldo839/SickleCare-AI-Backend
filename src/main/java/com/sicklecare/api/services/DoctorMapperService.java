package com.sicklecare.api.services;

import com.sicklecare.api.dtos.DoctorResponseDTO;
import com.sicklecare.api.models.Doctor;
import org.springframework.stereotype.Service;

@Service
public class DoctorMapperService {

    public DoctorResponseDTO mapToResponseDTO(Doctor doctor){

        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getUsername(),
                doctor.getEmail(),
                doctor.getFirstname() + " " + doctor.getLastname(),
                doctor.getRole(),
                doctor.getSpeciality(),
                doctor.getMatricule(),
                doctor.getValidationLetterReference(),
                doctor.getHospitalUnit(),
                doctor.getRegion(),
                doctor.getCity(),
                doctor.getHospital()

        );

    }

}
