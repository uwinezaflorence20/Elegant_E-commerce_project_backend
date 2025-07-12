package org.example.elegant_ecommerce_backend_project.Cart;

import org.example.elegant_ecommerce_backend_project.Dto.CartItemRequest;
import org.example.elegant_ecommerce_backend_project.Dto.CartItemResponse;
import org.example.elegant_ecommerce_backend_project.Dto.OrderDTO;
import org.example.elegant_ecommerce_backend_project.User.User;
import org.example.elegant_ecommerce_backend_project.User.UserRepository;
import org.example.elegant_ecommerce_backend_project.order.Order;
import org.example.elegant_ecommerce_backend_project.order.OrderItem;
import org.example.elegant_ecommerce_backend_project.order.OrderService;
import org.example.elegant_ecommerce_backend_project.order.OrderRepository;
import org.example.elegant_ecommerce_backend_project.product.Product;
import org.example.elegant_ecommerce_backend_project.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final OrderRepository orderRepo; // 💡 make sure this is injected
    private final OrderService orderService;

    public CartService(CartItemRepository cartItemRepo,
                       ProductRepository productRepo,
                       UserRepository userRepo,
                       OrderRepository orderRepo,
                       OrderService orderService) {
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.orderService = orderService;
    }

    public void addToCart(String username, CartItemRequest request) {
        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + request.getProductId()));

        CartItem item = cartItemRepo.findByUserAndProduct(user, product)
                .orElse(new CartItem());

        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(item.getQuantity() + request.getQuantity());

        cartItemRepo.save(item);
    }

    public List<CartItemResponse> getCartItems(String username) {
        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return cartItemRepo.findByUser(user).stream().map(item -> {
            CartItemResponse res = new CartItemResponse();
            res.setId(item.getId());
            res.setProductName(item.getProduct().getTitle());
            res.setQuantity(item.getQuantity());
            res.setProductPrice(item.getProduct().getPrice());
            res.setTotalPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return res;
        }).toList();
    }

    public void clearCart(String username) {
        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        cartItemRepo.deleteAll(cartItemRepo.findByUser(user));
    }

    public void updateCartItem(String username, Long cartItemId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        CartItem item = cartItemRepo.findById(cartItemId)
                .filter(i -> i.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        item.setQuantity(quantity);
        cartItemRepo.save(item);
    }

    public void removeCartItem(String username, Long cartItemId) {
        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        CartItem item = cartItemRepo.findById(cartItemId)
                .filter(i -> i.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItemRepo.delete(item);
    }

    @Transactional
    public OrderDTO checkoutCart(String username) {
        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepo.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            if (product.getQuantity() < quantity) {
                throw new RuntimeException("Not enough stock for " + product.getTitle());
            }

            product.setQuantity(product.getQuantity() - quantity);
            productRepo.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            orderItems.add(orderItem);

            total = total.add(orderItem.getTotalPrice());
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);
        orderRepo.save(order);

        cartItemRepo.deleteAll(cartItems);

        return orderService.convertToDTO(order);
    }
}
