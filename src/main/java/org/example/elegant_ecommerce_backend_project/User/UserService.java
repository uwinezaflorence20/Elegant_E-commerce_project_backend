package org.example.elegant_ecommerce_backend_project.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.elegant_ecommerce_backend_project.Util.EmailUtil;
import org.example.elegant_ecommerce_backend_project.Util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.example.elegant_ecommerce_backend_project.PasswordReset.PasswordResetToken;
import org.example.elegant_ecommerce_backend_project.PasswordReset.PasswordResetTokenRepository;


@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailUtil emailUtil;

    // Inside UserService class
    private final PasswordResetTokenRepository tokenRepository;

    public User registerUser(String fullName, String userName, String email, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(fullName, userName, email, encodedPassword);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }








    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

        // Generate token using JwtUtil
        String token = JwtUtil.generateToken(email);

        try {
            emailUtil.sendSetPasswordEmail(email, token);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send email, please try again");
        }

        return "Please check your email to set your password.";
    }








    public String setPasswordByToken(String token, String newPassword) {
        String email;
        try {
            email = JwtUtil.extractEmail(token);
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired reset token.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Optionally, invalidate token if you store tokens separately (you can skip this if not)

        return "Password reset successful.";
    }

}
