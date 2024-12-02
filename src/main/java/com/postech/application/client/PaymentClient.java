package com.postech.application.client;

import java.math.BigDecimal;

public interface PaymentClient {
    void enviarPagamento(Long clientId, Long pedidoId, BigDecimal valorTotal);
}
