package com.sicklecare.api.services;

import com.sicklecare.api.models.DoctorValidation;
import com.sicklecare.api.models.PatientValidation;
import com.sicklecare.api.models.Validation;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    public void send(Validation validation) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@sicklecareai.ai");
            message.setTo(validation.getUser().getEmail());
            message.setSubject("Your activation code");
            String text = String.format(
                    "Hello %s, \nyour activation code is %s \n See you on the application !",
                    validation.getUser().getUsername(),
                    validation.getCode()
            );
            message.setText(text);

            javaMailSender.send(message);

        } catch (MailException e) {
            System.err.println("Error: " + e.getMessage());
            System.out.println("Debug code: " + validation.getCode());
        }
    }

}
