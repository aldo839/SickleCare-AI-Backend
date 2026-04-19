package com.sicklecare.api.services;

import com.sicklecare.api.models.DoctorValidation;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    public void send(DoctorValidation doctorValidation) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@sicklecareai.ai");
            message.setTo(doctorValidation.getUser().getEmail());
            message.setSubject("Your activation code");

            String text = String.format(
                    "Hello %s, \nyour activation code is %s. \nSee you soon",
                    doctorValidation.getUser().getUsername(),
                    doctorValidation.getCode()
            );
            message.setText(text);

            javaMailSender.send(message);

        } catch (MailException e) {
            System.out.println("Error on email sending process : " + e.getMessage());
            System.out.println("Test code : " + doctorValidation.getCode());
        }

    }

}
