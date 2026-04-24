package com.sicklecare.api.services;

import com.sicklecare.api.config.JwtUtils;
import com.sicklecare.api.dtos.LoginRequestDTO;
import com.sicklecare.api.dtos.LoginResponseDTO;
import com.sicklecare.api.exceptions.BadCredentialsException;
import com.sicklecare.api.exceptions.ResourceNotFoundException;
import com.sicklecare.api.models.Admin;
import com.sicklecare.api.models.Doctor;
import com.sicklecare.api.models.User;
import com.sicklecare.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public LoginResponseDTO authenticate(LoginRequestDTO loginDTO) {

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found !"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad password !");
        }

        if (!user.getActivated()) {
            throw new RuntimeException("Unactivated account. Please verify your mails.");
        }

        String token = jwtUtils.generateToken(user);

        // For patient
        String firstname = null;
        String lastname = null;

        if (user instanceof Admin admin){
            firstname = admin.getFirstname();
            lastname = admin.getLastname();
        } else if (user instanceof Doctor doctor){
            firstname = doctor.getFirstname();
            lastname = doctor.getLastname();
        }

        return new LoginResponseDTO(
                token,
                user.getUsername(),
                user.getRole().name(),
                firstname,
                lastname
        );
    }
}
