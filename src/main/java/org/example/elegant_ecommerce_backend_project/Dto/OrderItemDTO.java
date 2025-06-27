package org.example.elegant_ecommerce_backend_project.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private Long productId;
    private Integer quantity;
    // getters/setters
}
