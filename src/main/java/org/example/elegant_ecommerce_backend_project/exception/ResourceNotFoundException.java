package org.example.elegant_ecommerce_backend_project.exception;

public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String resource) {
        super(resource + " not found.");
    }
}
