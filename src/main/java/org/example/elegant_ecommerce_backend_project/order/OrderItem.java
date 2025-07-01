package org.example.elegant_ecommerce_backend_project.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.elegant_ecommerce_backend_project.product.Product;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private Double price; // price at time of order

    // Getters and setters
}
