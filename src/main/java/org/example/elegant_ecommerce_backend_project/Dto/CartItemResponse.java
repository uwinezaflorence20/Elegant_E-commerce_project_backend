package org.example.elegant_ecommerce_backend_project.Dto;

import lombok.Data;

@Data

public class CartItemResponse {
    private Long id;
    private String productName;
    private int quantity;
}