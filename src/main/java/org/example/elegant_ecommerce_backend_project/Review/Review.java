package org.example.elegant_ecommerce_backend_project.Review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.elegant_ecommerce_backend_project.User.User;
import org.example.elegant_ecommerce_backend_project.product.Product;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private String comment;

    private int rating;
}
