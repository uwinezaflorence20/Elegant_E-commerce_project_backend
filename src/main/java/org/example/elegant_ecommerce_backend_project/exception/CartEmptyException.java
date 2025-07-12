package org.example.elegant_ecommerce_backend_project.exception;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException(String message) {
        super(message);
    }
}