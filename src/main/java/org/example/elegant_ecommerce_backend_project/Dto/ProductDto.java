package org.example.elegant_ecommerce_backend_project.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {
    private String title;
    private String description;
    private double price;
    private int quantity;
    private String size;
    private String color;
    private Long categoryId;

    @Schema(type = "string", format = "binary", description = "Product image file")
    private MultipartFile image;
}
