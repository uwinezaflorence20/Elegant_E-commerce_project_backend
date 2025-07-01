package org.example.elegant_ecommerce_backend_project.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.elegant_ecommerce_backend_project.Dto.OrderItemDTO;
import org.example.elegant_ecommerce_backend_project.User.User;
import org.example.elegant_ecommerce_backend_project.User.UserRepository;
import org.example.elegant_ecommerce_backend_project.product.Product;
import org.example.elegant_ecommerce_backend_project.product.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;  // Inject UserRepository here

    @Transactional
    public Order createOrder(List<OrderItemDTO> itemsDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Cast principal to UserDetails (Spring Security)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Fetch your custom User entity by username (or email depending on your setup)
        User currentUser = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + userDetails.getUsername()));

        Order order = new Order();
        order.setUser(currentUser);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO itemDTO : itemsDTO) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemDTO.getProductId()));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(product.getPrice());

            orderItems.add(item);
        }

        order.setItems(orderItems);

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User currentUser = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + userDetails.getUsername()));

        return orderRepository.findByUserId(currentUser.getId());
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}
