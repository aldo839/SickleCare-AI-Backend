package com.sicklecare.api.services;

import com.sicklecare.api.dtos.PatientResponseDTO;
import com.sicklecare.api.models.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class PatientMapperService {

    public PatientResponseDTO mapToResponseDTO(Patient patient){

        int age = 0;
        if (patient.getBirthDate() != null){
            age = Period.between(patient.getBirthDate(), LocalDate.now()).getYears();
        }
        return new PatientResponseDTO(
                patient.getId(),
                patient.getUsername(),
                patient.getEmail(),
                patient.getRole().name(),
                patient.getFirstname() + " " + patient.getLastname(),
                patient.getSex(),
                age,
                patient.getGenotype(),
                patient.getBloodType(),
                patient.getWeight(),
                patient.getRegion(),
                patient.getCity()
        );

    }

}
