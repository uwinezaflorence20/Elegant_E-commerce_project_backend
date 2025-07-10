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

    public void postReview(String email, ReviewRequest request) {
        User user = userRepo.findByEmail(email).orElseThrow();
        Product product = productRepo.findById(request.getProductId()).orElseThrow();

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setComment(request.getComment());
        review.setRating(request.getRating());
        reviewRepo.save(review);
    }

    public List<ReviewResponse> getReviewsForProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow();
        return reviewRepo.findByProduct(product).stream().map(r -> {
            ReviewResponse res = new ReviewResponse();
            res.setUsername(r.getUser().getUsername());
            res.setComment(r.getComment());
            res.setRating(r.getRating());
            return res;
        }).toList();
    }
}
