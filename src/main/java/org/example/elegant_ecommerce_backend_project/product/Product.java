package org.example.elegant_ecommerce_backend_project.product;

import jakarta.persistence.*;
import lombok.*;
import org.example.elegant_ecommerce_backend_project.categories.Category;

@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private double price;
    private int quantity;
    private String size;
    private String color;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}