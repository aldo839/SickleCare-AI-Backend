package com.sicklecare.api.services;

import com.sicklecare.api.dtos.MfaVerificationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class MfaService {

    private final StringRedisTemplate redisTemplate;
    private final NotificationService notificationService;

    private static final int CODE_EXPIRATION_MINUTES = 5;

    public void generateAndSendCode(String email, String username){

        SecureRandom secureRandom = new SecureRandom();
        String code = String.format("%06d", secureRandom.nextInt(1000000));

        redisTemplate.opsForValue().set(
                "mfa:" + email,
                code,
                Duration.ofMinutes(CODE_EXPIRATION_MINUTES)
        );

        System.out.println("Sending of validation code [" + code + "] to : " + email);
        notificationService.sendMfaCode(email, username, code);

    }

    public Boolean verifyCode(MfaVerificationRequestDTO mfaVerificationRequestDTO){

        String email = mfaVerificationRequestDTO.email();
        String userInputCode = mfaVerificationRequestDTO.code();

        String storedCode = redisTemplate.opsForValue().get("mfa:" + email);

        if (storedCode != null && storedCode.equals(userInputCode)){
            // We delete the code after use
            redisTemplate.delete("mfa:" + email);
            return true;
        }

        return false;
    }

}
