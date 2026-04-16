package com.sicklecare.api.services;

import com.sicklecare.api.dtos.LoginRequestDTO;
import com.sicklecare.api.exceptions.BadCredentialsException;
import com.sicklecare.api.exceptions.ResourceNotFoundException;
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

    public String authenticate(LoginRequestDTO loginDTO){

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found !"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Bad password !");
        }

        return "Connection successful for " + user.getUsername();
    }

}
