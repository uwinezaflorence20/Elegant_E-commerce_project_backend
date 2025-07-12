package org.example.elegant_ecommerce_backend_project.order;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.elegant_ecommerce_backend_project.Cart.CartService;
import org.example.elegant_ecommerce_backend_project.Dto.OrderDTO;
import org.example.elegant_ecommerce_backend_project.Dto.OrderItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "auth")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody List<OrderItemDTO> itemsDTO) {
        OrderDTO orderDTO = orderService.createOrder(itemsDTO);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> getUserOrders() {
        List<OrderDTO> ordersDTO = orderService.getOrdersByCurrentUser();
        return ResponseEntity.ok(ordersDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        OrderDTO updatedOrderDTO = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkout(@AuthenticationPrincipal UserDetails user) {
        OrderDTO order = cartService.checkoutCart(user.getUsername());
        return ResponseEntity.ok(order);
    }

}
