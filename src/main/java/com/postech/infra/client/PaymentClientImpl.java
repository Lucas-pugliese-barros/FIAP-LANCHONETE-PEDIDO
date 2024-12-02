package com.postech.infra.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.application.client.PaymentClient;
import com.postech.infra.dto.request.PaymentRequestDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PaymentClientImpl implements PaymentClient {

    private static final Logger LOG = Logger.getLogger(PaymentClientImpl.class.getName());

    private String paymentUrl;

    public PaymentClientImpl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    @Override
    public void enviarPagamento(Long clientId, Long pedidoId, BigDecimal valorTotal) {
        LOG.info("Preparango para enviar pagamento para a url: " + paymentUrl);
        LOG.info("Dados para o pagamento - clientId: " + clientId + ", pedidoId: " + pedidoId + ", valorTotal: " + valorTotal);

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(clientId, pedidoId, valorTotal);

        ObjectMapper objectMapper = new ObjectMapper();

        HttpResponse<String> response;
        try {
            HttpClient client = HttpClient.newHttpClient();

            String jsonRequestBody = objectMapper.writeValueAsString(paymentRequestDTO);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(paymentUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        LOG.info("Response Code: " + response.statusCode());
        LOG.info("Response Body: " + response.body());
    }
}
