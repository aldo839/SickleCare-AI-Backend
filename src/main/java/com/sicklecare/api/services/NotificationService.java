package com.sicklecare.api.services;

import com.sicklecare.api.models.PatientValidation;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    public void send(PatientValidation patientValidation) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@sicklecareai.ai");
            message.setTo(patientValidation.getUser().getUsername());
            message.setSubject("Your activation code");

            String text = String.format(
                    "Hello %s, \nyour activation code is %s; See you soon",
                    patientValidation.getUser().getUsername(),
                    patientValidation.getCode()
            );
            message.setText(text);

            javaMailSender.send(message);
        } catch (MailException e) {
            System.out.println("Error on mail sending process : " + e.getMessage());
            System.out.println("CODE POUR TEST : " + patientValidation.getCode());
        }


    }


}
