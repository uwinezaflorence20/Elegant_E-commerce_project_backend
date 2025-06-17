package org.example.elegant_ecommerce_backend_project.Util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSetPasswordEmail(String email, String token) throws MessagingException {
        String resetLink = "http://localhost:8080/api/users/resetPassword?token=" + token;

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Reset Your Password");
        mimeMessageHelper.setText("""
            <div>
            <p>Click the link below to reset your password:</p>
            <a href="%s">Reset Password</a>
            </div>
            """.formatted(resetLink), true);
        javaMailSender.send(mimeMessage);
    }

}
