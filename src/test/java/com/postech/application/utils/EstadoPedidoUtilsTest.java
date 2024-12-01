package com.postech.application.utils;

import com.postech.domain.enums.ErroPedidoEnum;
import com.postech.domain.enums.EstadoPedidoEnum;
import com.postech.domain.exceptions.PedidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EstadoPedidoUtilsTest {

    @Test
    public void testValidaEstadoRecebidoParaPago() {
        boolean result = EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.RECEBIDO, EstadoPedidoEnum.PAGO);

        assertTrue(result, "Deve ser permitido transitar de RECEBIDO para PAGO");
    }

    @Test
    public void testValidaEstadoRecebidoParaCancelado() {
        boolean result = EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.RECEBIDO, EstadoPedidoEnum.CANCELADO);

        assertTrue(result, "Deve ser permitido transitar de RECEBIDO para CANCELADO");
    }

    @Test
    public void testValidaEstadoPagoParaPreparando() {
        boolean result = EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.PAGO, EstadoPedidoEnum.PREPARANDO);

        assertTrue(result, "Deve ser permitido transitar de PAGO para PREPARANDO");
    }

    @Test
    public void testValidaEstadoPagoParaCancelado() {
        boolean result = EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.PAGO, EstadoPedidoEnum.CANCELADO);

        assertTrue(result, "Deve ser permitido transitar de PAGO para CANCELADO");
    }

    @Test
    public void testValidaEstadoPreparandoParaPronto() {
        boolean result = EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.PREPARANDO, EstadoPedidoEnum.PRONTO);

        assertTrue(result, "Deve ser permitido transitar de PREPARANDO para PRONTO");
    }

    @Test
    public void testValidaEstadoPreparandoParaCancelado() {
        boolean result = EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.PREPARANDO, EstadoPedidoEnum.CANCELADO);

        assertTrue(result, "Deve ser permitido transitar de PREPARANDO para CANCELADO");
    }

    @Test
    public void testValidaEstadoProntoParaFinalizado() {
        boolean result = EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.PRONTO, EstadoPedidoEnum.FINALIZADO);

        assertTrue(result, "Deve ser permitido transitar de PRONTO para FINALIZADO");
    }

    @Test
    public void testValidaEstadoProntoParaCancelado() {
        boolean result = EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.PRONTO, EstadoPedidoEnum.CANCELADO);

        assertTrue(result, "Deve ser permitido transitar de PRONTO para CANCELADO");
    }

    @Test
    public void testValidaEstadoFinalizadoNaoPodeMudar() {
        PedidoException exception = assertThrows(PedidoException.class, () -> {
            EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.FINALIZADO, EstadoPedidoEnum.PREPARANDO);
        });
        assertEquals(ErroPedidoEnum.ESTADO_INVALIDO, exception.getErro(), "Não deve ser permitido transitar de FINALIZADO para qualquer outro estado");
    }

    @Test
    public void testValidaEstadoCanceladoNaoPodeMudar() {
        PedidoException exception = assertThrows(PedidoException.class, () -> {
            EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.CANCELADO, EstadoPedidoEnum.PREPARANDO);
        });
        assertEquals(ErroPedidoEnum.ESTADO_INVALIDO, exception.getErro(), "Não deve ser permitido transitar de CANCELADO para qualquer outro estado");
    }

    @Test
    public void testValidaEstadoRecebidoParaPreparando() {
        boolean result = EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.RECEBIDO, EstadoPedidoEnum.PREPARANDO);

        assertFalse(result, "Não deve ser permitido transitar de RECEBIDO para PREPARANDO");
    }

    @Test
    public void testValidaEstadoPagoParaFinalizado() {
        boolean result = EstadoPedidoUtils.validaEstado(EstadoPedidoEnum.PAGO, EstadoPedidoEnum.FINALIZADO);

        assertFalse(result, "Não deve ser permitido transitar de PAGO para FINALIZADO");
    }
}
