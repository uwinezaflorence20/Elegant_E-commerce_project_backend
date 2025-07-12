package org.example.elegant_ecommerce_backend_project.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) { super(message); }
}
