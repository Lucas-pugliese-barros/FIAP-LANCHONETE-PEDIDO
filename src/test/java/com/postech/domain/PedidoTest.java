package com.postech.domain.entities;

import com.postech.domain.exceptions.DominioException;
import com.postech.domain.enums.EstadoPedidoEnum;
import com.postech.domain.utils.ValidacaoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoTest {

    private Pedido pedido;
    private PedidoProduto pedidoProdutoMock;

    @BeforeEach
    public void setUp() {
        pedidoProdutoMock = mock(PedidoProduto.class);
    }

    @Test
    public void testConstructorValidEstado() {
        List<PedidoProduto> produtos = List.of(pedidoProdutoMock);
        EstadoPedidoEnum estado = EstadoPedidoEnum.RECEBIDO;

        pedido = new Pedido(1L, 123L, estado, LocalDate.now(), produtos);

        assertNotNull(pedido, "Pedido should be created successfully with a valid state.");
        assertEquals(estado, pedido.getEstado(), "The state of the order should be set correctly.");
    }

    @Test
    public void testConstructorInvalidEstado() {
        List<PedidoProduto> produtos = List.of(pedidoProdutoMock);
        EstadoPedidoEnum estado = null;

        DominioException exception = assertThrows(DominioException.class, () -> {
            new Pedido(1L, 123L, estado, LocalDate.now(), produtos);
        });

        assertEquals("O estado do pedido não pode estar vazio!", exception.getMessage(), "Exception message should match");
    }

    @Test
    public void testGetValorPedidoValidCalculation() {
        PedidoProduto produtoMock = mock(PedidoProduto.class);
        Produto produtoMocked = mock(Produto.class);
        when(produtoMock.getProduto()).thenReturn(produtoMocked);
        when(produtoMocked.getPreco()).thenReturn(10.0);
        when(produtoMock.getQuantidade()).thenReturn(2);
        pedido = new Pedido(1L, 123L, EstadoPedidoEnum.RECEBIDO, LocalDate.now(), List.of(produtoMock));

        BigDecimal valorPedido = pedido.getValorPedido(pedido);

        assertEquals(BigDecimal.valueOf(20.0), valorPedido, "The order value should be calculated correctly.");
    }

    @Test
    public void testGetValorPedidoEmptyProductList() {
        pedido = new Pedido(1L, 123L, EstadoPedidoEnum.RECEBIDO, LocalDate.now(), List.of());

        BigDecimal valorPedido = pedido.getValorPedido(pedido);

        assertEquals(BigDecimal.valueOf(0.0), valorPedido, "The order value should be 0 if there are no products.");
    }

    @Test
    public void testGetValorPedidoSingleProduct() {
        Produto produtoMock = mock(Produto.class);
        when(produtoMock.getPreco()).thenReturn(50.0);
        PedidoProduto pedidoProduto = new PedidoProduto(1L, null, produtoMock, 3);
        pedido = new Pedido(1L, 123L, EstadoPedidoEnum.RECEBIDO, LocalDate.now(), List.of(pedidoProduto));
        pedidoProduto.setPedido(pedido);

        BigDecimal valorPedido = pedido.getValorPedido(pedido);

        assertEquals(BigDecimal.valueOf(150.0), valorPedido, "The order value should be calculated correctly for a single product.");
    }

    @Test
    public void testSetEstadoValidStateChange() {
        pedido = new Pedido(1L, 123L, EstadoPedidoEnum.RECEBIDO, LocalDate.now(), List.of(pedidoProdutoMock));
        EstadoPedidoEnum newEstado = EstadoPedidoEnum.PAGO;

        pedido.setEstado(newEstado);

        assertEquals(newEstado, pedido.getEstado(), "The state of the order should be updated correctly.");
    }
}
