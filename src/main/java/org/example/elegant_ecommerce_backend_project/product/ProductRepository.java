package org.example.elegant_ecommerce_backend_project.product;

import org.example.elegant_ecommerce_backend_project.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
