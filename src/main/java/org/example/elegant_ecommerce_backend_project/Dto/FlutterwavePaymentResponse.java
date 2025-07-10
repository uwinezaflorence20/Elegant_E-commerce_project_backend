package org.example.elegant_ecommerce_backend_project.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlutterwavePaymentResponse {
    private String status;
    private String message;
    private Data data;

    @Getter
    @Setter
    public static class Data {
        private String link;
    }
}