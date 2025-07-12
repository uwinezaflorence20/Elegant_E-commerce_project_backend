package org.example.elegant_ecommerce_backend_project.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {
    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal productPrice;
    private BigDecimal totalPrice;  // quantity * productPrice
}