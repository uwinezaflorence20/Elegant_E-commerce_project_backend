package org.example.elegant_ecommerce_backend_project.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String status;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;
    private List<OrderItemDTO> items;

    public OrderDTO(Long id, String status, LocalDateTime createdAt, Long userId) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.userId = userId;
    }
}
