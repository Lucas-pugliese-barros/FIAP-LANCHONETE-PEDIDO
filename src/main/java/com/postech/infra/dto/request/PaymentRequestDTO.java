package com.postech.infra.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentRequestDTO {

    Long clientId;
    Long pedidoId;
    BigDecimal valorTotal;

}
