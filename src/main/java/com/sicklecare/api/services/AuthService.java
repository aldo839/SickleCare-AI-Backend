package com.sicklecare.api.services;

import com.sicklecare.api.config.JwtUtils;
import com.sicklecare.api.dtos.LoginRequestDTO;
import com.sicklecare.api.dtos.LoginResponseDTO;
import com.sicklecare.api.dtos.MfaVerificationRequestDTO;
import com.sicklecare.api.exceptions.BadCredentialsException;
import com.sicklecare.api.exceptions.ResourceNotFoundException;
import com.sicklecare.api.models.Admin;
import com.sicklecare.api.models.Doctor;
import com.sicklecare.api.models.User;
import com.sicklecare.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final MfaService mfaService;

    public LoginResponseDTO authenticate(LoginRequestDTO loginDTO) {

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found !"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password !");
        }

        if (!user.getActivated()) {
            throw new RuntimeException("Unactivated account. Please verify your mails.");
        }

        mfaService.generateAndSendCode(user.getEmail(), user.getUsername());

        return new LoginResponseDTO(
                null, // the token is null because authentication is not complete
                user.getUsername(),
                user.getRole().name(),
                null,
                null,
                true
        );

    }

    public LoginResponseDTO verifyMfaGenerateToken(MfaVerificationRequestDTO request) {

        if (mfaService.verifyCode(request)) {
            User user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found !"));

            String token = jwtUtils.generateToken(user);

            // Fetch specifics information in user category.
            String firstname = (user instanceof Admin admin) ? admin.getFirstname() : (user instanceof Doctor doctor) ? doctor.getFirstname() : null;
            String lastname = (user instanceof Admin admin) ? admin.getLastname() : (user instanceof Doctor doctor) ? doctor.getLastname() : null;

            return new LoginResponseDTO(
                    token,
                    user.getUsername(),
                    user.getRole().name(),
                    firstname,
                    lastname,
                    false // MFA is not required again because the user is already connected
            );
        }

        throw new BadCredentialsException("Invalid or expired MFA code.");
    }

}
