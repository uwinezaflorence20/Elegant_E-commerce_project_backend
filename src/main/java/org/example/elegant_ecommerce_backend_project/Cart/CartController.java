package org.example.elegant_ecommerce_backend_project.Cart;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.elegant_ecommerce_backend_project.Dto.CartItemRequest;
import org.example.elegant_ecommerce_backend_project.Dto.CartItemResponse;
import org.example.elegant_ecommerce_backend_project.Dto.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@SecurityRequirement(name = "auth")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItemRequest req, @AuthenticationPrincipal UserDetails user) {
        cartService.addToCart(user.getUsername(), req);
        return ResponseEntity.ok("Added to cart");
    }

    @GetMapping
    public List<CartItemResponse> viewCart(@AuthenticationPrincipal UserDetails user) {
        return cartService.getCartItems(user.getUsername());
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@AuthenticationPrincipal UserDetails user) {
        cartService.clearCart(user.getUsername());
        return ResponseEntity.ok("Cart cleared");
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkout(@AuthenticationPrincipal UserDetails user) {
        OrderDTO order = cartService.checkoutCart(user.getUsername());
        return ResponseEntity.ok(order);
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable Long cartItemId,
            @RequestParam int quantity,
            @AuthenticationPrincipal UserDetails user) {
        cartService.updateCartItem(user.getUsername(), cartItemId, quantity);
        return ResponseEntity.ok("Cart item updated");
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId, @AuthenticationPrincipal UserDetails user) {
        cartService.removeCartItem(user.getUsername(), cartItemId);
        return ResponseEntity.ok("Cart item removed");
    }
}
