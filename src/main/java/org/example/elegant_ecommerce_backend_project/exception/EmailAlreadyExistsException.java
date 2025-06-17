package org.example.elegant_ecommerce_backend_project.exception;

public class EmailAlreadyExistsException extends ApiException {
    public EmailAlreadyExistsException() {
        super("Email already exists.");
    }
}
