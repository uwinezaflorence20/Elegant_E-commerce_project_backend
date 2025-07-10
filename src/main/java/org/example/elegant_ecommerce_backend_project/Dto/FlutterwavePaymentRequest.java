package org.example.elegant_ecommerce_backend_project.Dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlutterwavePaymentRequest {
    private String tx_ref;
    private double amount;
    private String currency = "RWF"; // or "USD"
    private String redirect_url;
    private String customer_email;
    private String customer_name;
    private String payment_options = "card,mobilemoneyrwanda";
}