package org.example.elegant_ecommerce_backend_project.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long productId;
    private Integer quantity;
}
