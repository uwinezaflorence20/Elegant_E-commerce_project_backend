package org.example.elegant_ecommerce_backend_project.Paynment;


import lombok.RequiredArgsConstructor;
import org.example.elegant_ecommerce_backend_project.Dto.FlutterwavePaymentRequest;
import org.example.elegant_ecommerce_backend_project.Dto.FlutterwavePaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FlutterwavePaymentService {

    @Value("${flutterwave.secretKey}")
    private String secretKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.flutterwave.com/v3")
            .defaultHeader("Authorization", "")
            .build();

    public Mono<FlutterwavePaymentResponse> initiatePayment(FlutterwavePaymentRequest request) {
        return webClient.post()
                .uri("/payments")
                .header("Authorization", "Bearer " + secretKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FlutterwavePaymentResponse.class);
    }
}
