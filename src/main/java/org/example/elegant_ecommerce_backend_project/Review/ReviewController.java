package org.example.elegant_ecommerce_backend_project.Review;

import lombok.RequiredArgsConstructor;
import org.example.elegant_ecommerce_backend_project.Dto.ReviewRequest;
import org.example.elegant_ecommerce_backend_project.Dto.ReviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> postReview(@RequestBody ReviewRequest request, @AuthenticationPrincipal UserDetails user) {
        reviewService.postReview(user.getUsername(), request);
        return ResponseEntity.ok("Review posted");
    }

    @GetMapping("/product/{productId}")
    public List<ReviewResponse> getReviews(@PathVariable Long productId) {
        return reviewService.getReviewsForProduct(productId);
    }
}
