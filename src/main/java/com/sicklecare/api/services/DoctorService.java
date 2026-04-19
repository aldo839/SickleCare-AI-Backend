package com.sicklecare.api.services;

import com.sicklecare.api.config.JwtUtils;
import com.sicklecare.api.dtos.DoctorRegistrationDTO;
import com.sicklecare.api.dtos.DoctorResponseDTO;
import com.sicklecare.api.dtos.DoctorUpdateDTO;
import com.sicklecare.api.exceptions.ResourceNotFoundException;
import com.sicklecare.api.exceptions.UserAlreadyExistsException;
import com.sicklecare.api.models.*;
import com.sicklecare.api.repository.DoctorRepository;
import com.sicklecare.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorMapperService doctorMapperService;
    private final DoctorValidationService doctorValidationService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

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

    // Fetch all doctor
    public List<DoctorResponseDTO> getAllDoctors(){

        return doctorRepository.findAll()
                .stream()
                .map(doctorMapperService::mapToResponseDTO)
                .toList();
    }

    // Fetch doctor by ID
    public DoctorResponseDTO getDoctorByID(Long id){

        return doctorRepository.findById(id)
                .map(doctorMapperService::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found !"));

    }

    // Update doctor
    public DoctorResponseDTO updateDoctor(Long id, DoctorUpdateDTO updateDTO){

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found !"));

        // Update only authorized field and if it's not null
        if (updateDTO.getUsername() != null && !updateDTO.getUsername().isEmpty()){
            doctor.setUsername(updateDTO.getUsername());
        }
        if (updateDTO.getEmail() != null && !updateDTO.getEmail().isEmpty()){
            doctor.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()){
            doctor.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }
        if (updateDTO.getHospitalUnit() != null && !updateDTO.getHospitalUnit().isEmpty()){
            doctor.setHospitalUnit(updateDTO.getHospitalUnit());
        }
        if (updateDTO.getRegion() != null && !updateDTO.getRegion().isEmpty()){
            doctor.setRegion(updateDTO.getRegion());
        }
        if (updateDTO.getCity() != null && !updateDTO.getCity().isEmpty()){
            doctor.setCity(updateDTO.getCity());
        }
        if (updateDTO.getHospital() != null && !updateDTO.getHospital().isEmpty()){
            doctor.setHospital(updateDTO.getHospital());
        }

        Doctor updateDoctor = doctorRepository.save(doctor);

        return doctorMapperService.mapToResponseDTO(updateDoctor);

    }

    // Delete doctor
    public void deleteDoctor(Long id){

        doctorRepository.deleteById(id);
    }

    // Activate a doctor account
    public String activateDoctor(Map<String, String> activation){

        String code = activation.get("code");

        DoctorValidation validation = doctorValidationService.readCode(code);

        if (Instant.now().isAfter(validation.getExpiration())){
            throw new RuntimeException("Time expired");
        }

        User user = validation.getUser();
        user.setActivated(true);
        userRepository.save(user);

        return jwtUtils.generateToken(user);
    }

}
