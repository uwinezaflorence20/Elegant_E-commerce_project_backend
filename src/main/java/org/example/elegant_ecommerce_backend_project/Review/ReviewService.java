package org.example.elegant_ecommerce_backend_project.Review;

import lombok.RequiredArgsConstructor;
import org.example.elegant_ecommerce_backend_project.Dto.ReviewRequest;
import org.example.elegant_ecommerce_backend_project.Dto.ReviewResponse;
import org.example.elegant_ecommerce_backend_project.User.User;
import org.example.elegant_ecommerce_backend_project.User.UserRepository;
import org.example.elegant_ecommerce_backend_project.product.Product;
import org.example.elegant_ecommerce_backend_project.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public void postReview(String username, ReviewRequest request) {
        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + request.getProductId()));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setComment(request.getComment());
        review.setRating(request.getRating());

        reviewRepo.save(review);
    }

    public List<ReviewResponse> getReviewsForProduct(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        return reviewRepo.findByProduct(product).stream().map(r -> {
            ReviewResponse res = new ReviewResponse();
            res.setUsername(r.getUser().getUsername());
            res.setComment(r.getComment());
            res.setRating(r.getRating());
            return res;
        }).toList();
    }
}
