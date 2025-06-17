package org.example.elegant_ecommerce_backend_project.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
