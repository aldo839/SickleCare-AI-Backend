package com.sicklecare.api.services;

import com.sicklecare.api.dtos.AdminRegistrationDTO;
import com.sicklecare.api.dtos.AdminResponseDTO;
import com.sicklecare.api.dtos.AdminUpdateDTO;
import com.sicklecare.api.exceptions.ResourceNotFoundException;
import com.sicklecare.api.exceptions.UserAlreadyExistsException;
import com.sicklecare.api.models.Admin;
import com.sicklecare.api.models.Role;
import com.sicklecare.api.repository.AdminRepository;
import com.sicklecare.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Value("${sicklecare.root.username}")
    private String rootUsername;

    @Value("${sicklecare.root.password}")
    private String rootPassword;

    @Value("${sicklecare.root.email}")
    private String rootEmail;

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminMapperService adminMapperService;

    // Initialization of a root account

    @Transactional(rollbackFor = Exception.class)
    public void initRootAccount(){

        if (!userRepository.existsByRole(Role.ROOT)) {

            Admin root = new Admin();
            root.setUsername(rootUsername);
            root.setEmail(rootEmail);
            root.setFirstname("System");
            root.setLastname("Root");
            root.setPassword(passwordEncoder.encode(rootPassword));
            root.setRole(Role.ROOT);
            root.setActivated(true);
            root.setValidated(true);

            userRepository.save(root);

            System.out.println("Root account initialized as well for : " + rootUsername);
        }
    }

    // Creation of admin

    @Transactional(rollbackFor = Exception.class)
    public AdminResponseDTO createAdmin(AdminRegistrationDTO dto){

        if (userRepository.existsByUsername(dto.getUserName())){
            throw new UserAlreadyExistsException("Username " + dto.getUserName() +" already exists!");
        }
        if (userRepository.existsByEmail(dto.getEmail())){
            throw new UserAlreadyExistsException("Email already exists!");
        }

        Admin admin = new Admin();
        admin.setUsername(dto.getUserName());
        admin.setEmail(dto.getEmail());
        admin.setFirstname(dto.getFirstname());
        admin.setLastname(dto.getLastname());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRole(Role.ADMIN);
        admin.setActivated(true);
        admin.setValidated(true);

        Admin createdAdmin = userRepository.save(admin);

        return adminMapperService.mapToResponseDTO(createdAdmin);
    }

    // Fetch all doctor
    public List<AdminResponseDTO> getAllAdmin(){

        return adminRepository.findAll()
                .stream()
                .map(adminMapperService::mapToResponseDTO)
                .toList();
    }

    // Fetch doctor by ID
    public AdminResponseDTO getAdminByID(Long id){

        return adminRepository.findById(id)
                .map(adminMapperService::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found !"));
    }

    // Update admin
    public AdminResponseDTO updateAdmin(Long id, AdminUpdateDTO updateDTO){

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found !"));

        // Update only authorized field and if it's not null
        if (updateDTO.getUsername() != null && !updateDTO.getUsername().isEmpty()){
            admin.setUsername(updateDTO.getUsername());
        }
        if (updateDTO.getEmail() != null && !updateDTO.getEmail().isEmpty()){
            admin.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()){
            admin.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        Admin updateAdmin = adminRepository.save(admin);

        return adminMapperService.mapToResponseDTO(updateAdmin);

    }

    // Delete admin
    public void deleteAdmin(Long id){

        adminRepository.deleteById(id);
    }

}
