package org.example.elegant_ecommerce_backend_project.Dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class LoginResponse {
    private String token;
    private String message;

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
