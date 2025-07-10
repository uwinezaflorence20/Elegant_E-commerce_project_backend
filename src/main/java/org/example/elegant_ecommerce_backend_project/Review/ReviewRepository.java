package org.example.elegant_ecommerce_backend_project.Review;

import org.example.elegant_ecommerce_backend_project.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
}
