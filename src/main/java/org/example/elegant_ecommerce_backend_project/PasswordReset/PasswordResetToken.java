package org.example.elegant_ecommerce_backend_project.PasswordReset;

import jakarta.persistence.*;
import lombok.*;
import org.example.elegant_ecommerce_backend_project.User.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiryDate;

    @OneToOne
    private User user;
}
