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

    public void sendSetPasswordEmail(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true); // Add 'true' for HTML

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Set Password");
        mimeMessageHelper.setText("""
                <div>
                    <a href="http://localhost:8080/set-password?email=%s" target="_blank">Click to set password</a>
                </div>
                """.formatted(email), true); // 'true' enables HTML

        javaMailSender.send(mimeMessage);
    }
}
