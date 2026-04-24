package com.sicklecare.api.services;

import com.sicklecare.api.dtos.AdminResponseDTO;
import com.sicklecare.api.models.Admin;
import org.springframework.stereotype.Service;

@Service
public class AdminMapperService {

    public AdminResponseDTO mapToResponseDTO(Admin admin){

        return new AdminResponseDTO(
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getFirstname() + admin.getLastname(),
                admin.getRole()
        );

    }

}
