package org.example.elegant_ecommerce_backend_project.Cart;

import org.example.elegant_ecommerce_backend_project.User.User;
import org.example.elegant_ecommerce_backend_project.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndProduct(User user, Product product);
}
