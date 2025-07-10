package org.example.elegant_ecommerce_backend_project.Paynment;


import lombok.RequiredArgsConstructor;
import org.example.elegant_ecommerce_backend_project.Dto.FlutterwavePaymentRequest;
import org.example.elegant_ecommerce_backend_project.Dto.FlutterwavePaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/flutterwave")
@RequiredArgsConstructor
public class FlutterwavePaymentController {

    private final FlutterwavePaymentService flutterwavePaymentService;

    @PostMapping("/pay")
    public Mono<ResponseEntity<FlutterwavePaymentResponse>> pay(@RequestBody FlutterwavePaymentRequest request) {
        return flutterwavePaymentService.initiatePayment(request)
                .map(ResponseEntity::ok);
    }
}
