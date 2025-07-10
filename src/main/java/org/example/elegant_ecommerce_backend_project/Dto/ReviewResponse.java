package org.example.elegant_ecommerce_backend_project.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {
    private String username;
    private String comment;
    private int rating;
}
