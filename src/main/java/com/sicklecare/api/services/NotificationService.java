package com.sicklecare.api.services;

import com.sicklecare.api.models.Role;
import com.sicklecare.api.models.User;
import com.sicklecare.api.models.Validation;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    public void sendActivationCode(Validation validation) {

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

    @Async
    public void sendAdminValidationSuccess(User user) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@sicklecareai.ai");
            message.setTo(user.getEmail());
            message.setSubject("Account Validated - SickleCare");

            if (user.getRole() == Role.DOCTOR) {
                message.setSubject("SickleCare Doctor Account Validation");
                String text = String.format(
                        "Hello %s, \nYour account has been officially validated by our administration. " +
                                "You can now access all features of SickleCare AI." +
                                "\n You can now be selected by patients on the App !",
                        user.getUsername()
                );
            } else {
                message.setSubject("SickleCare Patient Account Validation");
                String text = String.format(
                        "Hello %s, \nYour account has been officially validated by our administration. " +
                                "You can now access all features of SickleCare AI." +
                                "\n See you on the App to select your doctor !",
                        user.getUsername()
                );
            }

            String text = String.format(
                    "Hello %s, \nYour account has been officially validated by our administration. " +
                            "You can now access all features of SickleCare AI.",
                    user.getUsername()
            );
            message.setText(text);
            javaMailSender.send(message);

        } catch (MailException e) {
            System.err.println("SMTP Error (Admin Validation): " + e.getMessage());
        }
    }

    public void sendMfaCode(String email, String username, String code){

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@sicklecareai.ai");
            message.setTo(email);
            message.setSubject("Your activation code");
            String text = String.format(
                    "Hello %s, \n\nFor security reasons, please use the following code to complete your login: \n\n" +
                    "CODE: %s \n\n" +
                    "This code will expire in 5 minutes. If you did not request this, please secure your account.",
                    username,
                    code
            );
            message.setText(text);

            javaMailSender.send(message);

        } catch (MailException e) {
            System.err.println("Error: " + e.getMessage());
            System.out.println("MFA Mail Error: " + code);
        }
    }

}
