package org.example.elegant_ecommerce_backend_project.Dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
}
