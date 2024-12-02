package com.postech.infra.dto.response;

import com.postech.domain.enums.EstadoPedidoEnum;

import java.time.LocalDate;
import java.util.List;

public record PedidoResponseDTO(Long id,
                                Long clienteId,
                                EstadoPedidoEnum estado,
                                List<PedidoProdutoResponseDTO> pedidosProdutos,
                                LocalDate dataDoPagamento) {
}
