package org.example.elegant_ecommerce_backend_project.Dto;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long productId;
    private int quantity;
}
