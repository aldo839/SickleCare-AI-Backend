package com.sicklecare.api.services;

import com.sicklecare.api.models.PatientValidation;
import com.sicklecare.api.models.User;
import com.sicklecare.api.repository.PatientValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PatientValidationService{

    private final PatientValidationRepository patientValidationRepository;
    private final NotificationService notificationService;

    public void save(User user) {

        PatientValidation patientValidation = new PatientValidation();

        patientValidation.setUser(user);
        Instant creation = Instant.now();
        patientValidation.setCreation(creation);
        Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
        patientValidation.setExpiration(expiration);
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        patientValidation.setCode(code);

        patientValidationRepository.save(patientValidation);

        try {
            notificationService.sendActivationCode(patientValidation);
        } catch (Exception e) {
            System.err.println("Problem to send email : " + e.getMessage());
        }

    }

    public PatientValidation readCode(String code) {
        return patientValidationRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid code"));
    }

}
