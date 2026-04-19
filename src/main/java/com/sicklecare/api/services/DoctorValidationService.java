package com.sicklecare.api.services;

import com.sicklecare.api.models.DoctorValidation;
import com.sicklecare.api.models.User;
import com.sicklecare.api.repository.DoctorValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DoctorValidationService {

    private final DoctorValidationRepository doctorValidationRepository;
    private final NotificationService notificationService;

    public void save(User user) {

        DoctorValidation doctorValidation = new DoctorValidation();

        doctorValidation.setUser(user);
        Instant creation = Instant.now();
        doctorValidation.setCreation(creation);
        Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
        doctorValidation.setExpiration(expiration);
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        doctorValidation.setCode(code);

        doctorValidationRepository.save(doctorValidation);

        try {
            notificationService.send(doctorValidation);
        } catch (Exception e) {
            System.out.println("Problem to send email : " + e.getMessage());
        }

    }

}
