//package org.example.elegant_ecommerce_backend_project.order;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.userdetails.User;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "orders")
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // The user who placed the order
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    // Order status, e.g. PENDING, SHIPPED, DELIVERED, CANCELLED
//    private String status;
//
//    // Date order was created
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    // One order has many order items
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private List<OrderItem> items = new ArrayList<>();
//
//    // Getters and setters
//}
