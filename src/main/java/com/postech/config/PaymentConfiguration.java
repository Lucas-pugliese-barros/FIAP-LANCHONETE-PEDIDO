package com.postech.config;

import com.postech.infra.client.PaymentClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {


    @Value("${backend.payment.url}")
    private String paymentUrl;

    @Bean
    PaymentClientImpl paymentClient() {
        return new PaymentClientImpl(paymentUrl);
    }
}
