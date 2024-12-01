package com.postech.domain.enums;

public enum EstadoPedidoEnum {

    RECEBIDO(1),
    PAGO(2),
    PREPARANDO(3),
    PRONTO(4),
    FINALIZADO(5),
    CANCELADO(null);

    final Integer ordem;

    EstadoPedidoEnum(Integer ordem) {
        this.ordem = ordem;
    }

    public Integer getOrdem() {
        return ordem;
    }
}
