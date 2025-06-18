package org.example.elegant_ecommerce_backend_project.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "user_table")
@AllArgsConstructor
@NoArgsConstructor
// user tables by milli
public class User implements UserDetails {
    public User(String fullName, String UserName, String email, String password) {
        this.fullName = fullName;
        this.UserName = UserName;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Schema(required = true, maxLength = 50)
    private String fullName;
    @NotBlank
    @Schema(required = true, maxLength =50)
    private String UserName;
    @NotBlank
    @Schema(required = true, maxLength = 50)
    @Column(unique = true)
    private String email;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.UserName;

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;

    }


}
