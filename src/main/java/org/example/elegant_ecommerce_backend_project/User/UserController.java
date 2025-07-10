package org.example.elegant_ecommerce_backend_project.User;

import org.example.elegant_ecommerce_backend_project.Dto.LoginRequest;
import org.example.elegant_ecommerce_backend_project.Dto.LoginResponse;
import org.example.elegant_ecommerce_backend_project.Dto.RegisterRequest;
import org.example.elegant_ecommerce_backend_project.Config.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import org.example.elegant_ecommerce_backend_project.exception.EmailAlreadyExistsException;
import org.example.elegant_ecommerce_backend_project.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "User endpoint")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        User user = userService.registerUser(
                request.getFullName(),
                request.getUserName(),
                request.getEmail(),
                request.getPassword()

        );
        return ResponseEntity.ok(user);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.loginUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            throw new InvalidCredentialsException();
        }
    }


    @PutMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword
    ) {
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match.");
        }

        return new ResponseEntity<>(userService.setPasswordByToken(token, newPassword), HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> userOpt = userService.findByEmail(userDetails.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            HashMap<String, Object> profile = new HashMap<>();
            profile.put("fullName", user.getFullName());
            profile.put("username", user.getUsername());
            profile.put("email", user.getEmail());
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Optionally track logout in logs or DB
        return ResponseEntity.ok("Logged out successfully. Please clear the token on the client side.");
    }


}