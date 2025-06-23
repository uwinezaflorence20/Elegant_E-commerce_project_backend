package org.example.elegant_ecommerce_backend_project.User;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.elegant_ecommerce_backend_project.Dto.LoginResponse;
import org.example.elegant_ecommerce_backend_project.PasswordReset.PasswordResetTokenRepository;
import org.example.elegant_ecommerce_backend_project.Util.EmailUtil;
import org.example.elegant_ecommerce_backend_project.Util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailUtil emailUtil;
    private final PasswordResetTokenRepository tokenRepository;

    private final String adminEmail = "florenceuwineza36@gmail.com";

    public User registerUser(String fullName, String userName, String email, String rawPassword) {
        String role = email.equalsIgnoreCase(adminEmail) ? "ROLE_ADMIN" : "ROLE_USER";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(fullName, userName, email, encodedPassword, role);
        return userRepository.save(user);
    }

    public LoginResponse loginUser(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        String token = JwtUtil.generateToken(email);

        String message = "ROLE_ADMIN".equalsIgnoreCase(user.getRole())
                ? "Logged in as Admin"
                : "Logged in as User";

        return new LoginResponse(token, message);
    }
    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

        String token = JwtUtil.generateToken(email);
        try {
            emailUtil.sendSetPasswordEmail(email, token);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send email, please try again");
        }

        return "Please check your email to set your password.";
    }

    // Reset password using token
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
        return "Password reset successful.";
    }

    // Basic utility methods
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public String deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "User with ID " + id + " deleted successfully.";
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

}
