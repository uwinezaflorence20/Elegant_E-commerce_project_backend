package org.example.elegant_ecommerce_backend_project.User;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.elegant_ecommerce_backend_project.Util.EmailUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailUtil emailUtil;

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
        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

        try {
            emailUtil.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send email, please try again");
        }

        return "Please check your email to set your password.";
    }
}
