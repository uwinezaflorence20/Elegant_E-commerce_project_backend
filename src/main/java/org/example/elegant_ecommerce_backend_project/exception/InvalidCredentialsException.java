package org.example.elegant_ecommerce_backend_project.exception;

public class InvalidCredentialsException extends ApiException {
    public InvalidCredentialsException() {
        super("Invalid email or password.");
    }
}
