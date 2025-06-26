package org.example.elegant_ecommerce_backend_project.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    @NotBlank(message = "Category name is required")
    private String categoryName;

    @NotBlank(message = "Description is required")
    private String description;
}
