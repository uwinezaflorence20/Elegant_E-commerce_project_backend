//package org.example.elegant_ecommerce_backend_project.order;
//
//import lombok.RequiredArgsConstructor;
//import org.example.elegant_ecommerce_backend_project.Dto.OrderItemDTO;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/orders")
//@RequiredArgsConstructor
//public class OrderController {
//
//    private final OrderService orderService;
//
//    @PostMapping
//    public ResponseEntity<Order> createOrder(@RequestBody List<OrderItemDTO> itemsDTO) {
//        Order order = orderService.createOrder(itemsDTO);
//        return new ResponseEntity<>(order, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
//        Order order = orderService.getOrderById(id);
//        return ResponseEntity.ok(order);
//    }
//
//    @GetMapping("/user")
//    public ResponseEntity<List<Order>> getUserOrders() {
//        List<Order> orders = orderService.getOrdersByCurrentUser();
//        return ResponseEntity.ok(orders);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
//        Order updatedOrder = orderService.updateOrderStatus(id, status);
//        return ResponseEntity.ok(updatedOrder);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
//        orderService.cancelOrder(id);
//        return ResponseEntity.noContent().build();
//    }
//}
