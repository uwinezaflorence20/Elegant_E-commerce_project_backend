package org.example.elegant_ecommerce_backend_project.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.elegant_ecommerce_backend_project.Dto.OrderDTO;
import org.example.elegant_ecommerce_backend_project.Dto.OrderItemDTO;
import org.example.elegant_ecommerce_backend_project.User.User;
import org.example.elegant_ecommerce_backend_project.User.UserRepository;
import org.example.elegant_ecommerce_backend_project.product.Product;
import org.example.elegant_ecommerce_backend_project.product.ProductRepository;
import org.example.elegant_ecommerce_backend_project.exception.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderDTO createOrder(List<OrderItemDTO> itemsDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        Order order = new Order();
        order.setUser(currentUser);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemDTO itemDTO : itemsDTO) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + itemDTO.getProductId()));

            int requestedQty = itemDTO.getQuantity();
            int availableQty = product.getQuantity();

            if (availableQty < requestedQty) {
                throw new InsufficientStockException(
                        "Not enough stock for product: " + product.getTitle() +
                                " (available: " + availableQty + ", requested: " + requestedQty + ")"
                );
            }

            product.setQuantity(availableQty - requestedQty);
            productRepository.save(product);

            BigDecimal itemTotalPrice = product.getPrice().multiply(BigDecimal.valueOf(requestedQty));
            totalPrice = totalPrice.add(itemTotalPrice);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(requestedQty);
            item.setTotalPrice(itemTotalPrice);

            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        return convertToDTO(savedOrder);
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        return convertToDTO(order);
    }

    public List<OrderDTO> getOrdersByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        List<Order> orders = orderRepository.findByUserId(currentUser.getId());

        List<OrderDTO> ordersDTO = new ArrayList<>();
        for (Order order : orders) {
            ordersDTO.add(convertToDTO(order));
        }

        return ordersDTO;
    }

    public OrderDTO updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }

    public OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUserId(order.getUser().getId());
        dto.setUserName(order.getUser().getFullName());  // full name here
        dto.setTotalPrice(order.getTotalPrice());

        List<OrderItemDTO> itemsDTO = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setQuantity(item.getQuantity());
            itemsDTO.add(itemDTO);
        }

        dto.setItems(itemsDTO);
        return dto;
    }
}
