package org.example.elegant_ecommerce_backend_project.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) { super(message); }
}