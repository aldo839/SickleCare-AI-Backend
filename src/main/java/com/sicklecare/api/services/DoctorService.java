package com.sicklecare.api.services;

import com.sicklecare.api.dtos.DoctorRegistrationDTO;
import com.sicklecare.api.dtos.DoctorResponseDTO;
import com.sicklecare.api.exceptions.UserAlreadyExistsException;
import com.sicklecare.api.models.Doctor;
import com.sicklecare.api.models.Role;
import com.sicklecare.api.repository.DoctorRepository;
import com.sicklecare.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorMapperService doctorMapperService;
    private final PasswordEncoder passwordEncoder;

    public DoctorResponseDTO registerDoctor(DoctorRegistrationDTO dto){

        if (userRepository.existsByUsername(dto.getUsername())){
            System.out.println("Error : " + dto.getUsername() + " already exist !");
            throw new UserAlreadyExistsException("This username already exist !");
        }

        if (userRepository.existsByEmail(dto.getEmail())){
            System.out.println("Error : " + dto.getEmail() + " already exist !");
            throw new UserAlreadyExistsException("Email already exists!");
        }

        Doctor doctor = new Doctor();

        doctor.setUsername(dto.getUsername());
        doctor.setEmail(dto.getEmail());
        doctor.setPassword(passwordEncoder.encode(dto.getPassword()));
        doctor.setFirstname(dto.getFirstname());
        doctor.setLastname(dto.getLastname());
        doctor.setSpeciality(dto.getSpeciality());
        doctor.setMatricule(dto.getMatricule());
        doctor.setValidationLetterReference(dto.getValidationLetterReference());
        doctor.setHospitalUnit(dto.getHospitalUnit());
        doctor.setRegion(dto.getRegion());
        doctor.setCity(dto.getCity());
        doctor.setHospital(dto.getHospital());

        doctor.setRole(Role.DOCTOR);
        doctor.setActivated(false);
        doctor.setValidated(false);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapperService.mapToResponseDTO(savedDoctor);

    }

}
