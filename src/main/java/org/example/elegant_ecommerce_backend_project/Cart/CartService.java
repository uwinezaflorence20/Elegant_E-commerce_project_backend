package org.example.elegant_ecommerce_backend_project.Cart;

import org.example.elegant_ecommerce_backend_project.Dto.CartItemRequest;
import org.example.elegant_ecommerce_backend_project.Dto.CartItemResponse;
import org.example.elegant_ecommerce_backend_project.User.User;
import org.example.elegant_ecommerce_backend_project.User.UserRepository;
import org.example.elegant_ecommerce_backend_project.product.Product;
import org.example.elegant_ecommerce_backend_project.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartItemRepository cartItemRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public CartService(CartItemRepository cartItemRepo, ProductRepository productRepo, UserRepository userRepo) {
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public void addToCart(String email, CartItemRequest request) {
        User user = userRepo.findByEmail(email).orElseThrow();
        Product product = productRepo.findById(request.getProductId()).orElseThrow();

        CartItem item = cartItemRepo.findByUserAndProduct(user, product)
                .orElse(new CartItem());
        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(item.getQuantity() + request.getQuantity());
        cartItemRepo.save(item);
    }

    public List<CartItemResponse> getCartItems(String email) {
        User user = userRepo.findByEmail(email).orElseThrow();
        return cartItemRepo.findByUser(user).stream().map(item -> {
            CartItemResponse res = new CartItemResponse();
            res.setId(item.getId());
            res.setProductName(item.getProduct().getTitle());
            res.setQuantity(item.getQuantity());
            return res;
        }).toList();
    }

    public void clearCart(String email) {
        User user = userRepo.findByEmail(email).orElseThrow();
        cartItemRepo.deleteAll(cartItemRepo.findByUser(user));
    }
}
