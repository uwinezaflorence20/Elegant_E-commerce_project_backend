package org.example.elegant_ecommerce_backend_project.order;

import jakarta.persistence.*;
import lombok.*;
import org.example.elegant_ecommerce_backend_project.User.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who placed the order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Order status: PENDING, SHIPPED, DELIVERED, CANCELLED
    @Column(nullable = false)
    private String status;

    // Order creation time
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // One order has many items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
