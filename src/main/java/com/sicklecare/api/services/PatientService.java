package com.sicklecare.api.services;

import com.sicklecare.api.config.JwtUtils;
import com.sicklecare.api.dtos.PatientRegistrationDTO;
import com.sicklecare.api.dtos.PatientResponseDTO;
import com.sicklecare.api.dtos.PatientUpdateDTO;
import com.sicklecare.api.exceptions.ResourceNotFoundException;
import com.sicklecare.api.exceptions.UserAlreadyExistsException;
import com.sicklecare.api.models.Patient;
import com.sicklecare.api.models.PatientValidation;
import com.sicklecare.api.models.Role;
import com.sicklecare.api.models.User;
import com.sicklecare.api.repository.PatientRepository;
import com.sicklecare.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PatientMapperService patientMapperService;
    private final PasswordEncoder passwordEncoder;
    private final PatientValidationService patientValidationService;
    private final JwtUtils jwtUtils;

    public PatientResponseDTO registerPatient(PatientRegistrationDTO dto) {

        // Existence verification
        if (userRepository.existsByUsername(dto.getUsername())){
            System.out.println("Error : " + dto.getUsername() + "exist");
            throw new UserAlreadyExistsException("This user name already exists!");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            System.out.println("Error : " + dto.getEmail() + "exist");
            throw new UserAlreadyExistsException("Email already exists!");
        }

        Patient patient = new Patient();
        patient.setUsername(dto.getUsername());
        patient.setEmail(dto.getEmail());
        patient.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Mapping of all fields
        patient.setFirstname(dto.getFirstname());
        patient.setLastname(dto.getLastname());
        patient.setSex(dto.getSex());
        patient.setGenotype(dto.getGenotype());
        patient.setBirthDate(dto.getBirthDate());
        patient.setBloodType(dto.getBloodType());
        patient.setWeight(dto.getWeight());
        patient.setRegion(dto.getRegion());
        patient.setCity(dto.getCity());

        patient.setRole(Role.PATIENT);
        patient.setActivated(false);
        patient.setValidated(false);

        // Save patient
        Patient savedPatient = patientRepository.save(patient);

        // validation
        try {
            patientValidationService.save(savedPatient);
        } catch (Exception e) {
            System.err.println("Validation error : " + e.getMessage());
        }

        return patientMapperService.mapToResponseDTO(savedPatient);
    }

    // Fetch all patient
    public List<PatientResponseDTO> getAllPatients(){

        return patientRepository.findAll()
                .stream()
                .map(patientMapperService::mapToResponseDTO)
                .toList();
    }

    // Fetch patient by ID
    public PatientResponseDTO getPatientByID(Long id){

        return patientRepository.findById(id)
                .map(patientMapperService::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found !"));

    }

    // Update patient
    public PatientResponseDTO updatePatient(Long id, PatientUpdateDTO updateDTO){

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found !"));

        // Update only authorized field and if it's not null
        if (updateDTO.getUsername() != null && !updateDTO.getUsername().isEmpty()){
            patient.setUsername(updateDTO.getUsername());
        }
        if (updateDTO.getEmail() != null && !updateDTO.getEmail().isEmpty()){
            patient.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()){
            patient.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }
        if (updateDTO.getWeight() != null && updateDTO.getWeight() > 0){
            patient.setWeight(updateDTO.getWeight());
        }
        if (updateDTO.getRegion() != null && !updateDTO.getRegion().isEmpty()){
            patient.setRegion(updateDTO.getRegion());
        }
        if (updateDTO.getCity() != null && !updateDTO.getCity().isEmpty()){
            patient.setCity(updateDTO.getCity());
        }

        Patient updatePatient = patientRepository.save(patient);
        return patientMapperService.mapToResponseDTO(updatePatient);

    }

    // Delete patient
    public void deletePatient(Long id){

        patientRepository.deleteById(id);
    }

    // Activate a patient account
    public String activatePatient(Map<String, String> activation){

        String code = activation.get("code");

        PatientValidation validation = patientValidationService.readCode(code);

        if (Instant.now().isAfter(validation.getExpiration())){
            throw new RuntimeException("Time expired");
        }

        User user = validation.getUser();
        user.setActivated(true);
        userRepository.save(user);

        return jwtUtils.generateToken(user);
    }

}
