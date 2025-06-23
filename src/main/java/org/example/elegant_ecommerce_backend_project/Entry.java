package org.example.elegant_ecommerce_backend_project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Entry {
    @GetMapping
    public String entry() {
        return "<h1>Welcome to Elegant Ecommerce Backend API</h1>" +
                "<p>See the <a href='https://elegant-be.onrender.com/swagger-ui/index.html#/'>API Documentation</a></p>";
    }
}
