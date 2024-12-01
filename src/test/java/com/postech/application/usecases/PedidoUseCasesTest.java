package com.postech.application.usecases;

import com.postech.application.gateways.RepositorioDePedidoGateway;
import com.postech.domain.entities.Pedido;
import com.postech.domain.entities.PedidoProduto;
import com.postech.domain.entities.Produto;
import com.postech.domain.enums.CategoriaProdutoEnum;
import com.postech.domain.enums.EstadoPedidoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PedidoUseCasesTest {

    @Mock
    private RepositorioDePedidoGateway repositorioDePedido;

    @Mock
    private ProdutoUseCases produtoUseCases;

    @Spy
    @InjectMocks
    public PedidoUseCases pedidoUseCases;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOrdenarListarPedidosPorAntiguidade(){
        Produto produto = getProduto(1L, "LANCHE", "LANCHE", CategoriaProdutoEnum.LANCHE, 2.0);
        Pedido pedido = getPedido(1L, 1L, EstadoPedidoEnum.RECEBIDO, null);
        PedidoProduto pedidoProduto = getPedidoProduto(1L, pedido, produto, 1);
        pedido.setPedidosProdutos(List.of(pedidoProduto));


        Produto produto2 = getProduto(1L, "SOBREMESA", "SOBREMESA", CategoriaProdutoEnum.SOBREMESA, 2.0);
        Pedido pedido2 = getPedido(2L, 1L, EstadoPedidoEnum.RECEBIDO, null);
        PedidoProduto pedidoProduto2 = getPedidoProduto(2L, pedido2, produto2, 1);
        pedido2.setPedidosProdutos(List.of(pedidoProduto2));

        Produto produto3 = getProduto(1L, "BEBIDA", "BEBIDA", CategoriaProdutoEnum.BEBIDA, 2.0);
        Pedido pedido3 = getPedido(3L, 1L, EstadoPedidoEnum.RECEBIDO, null);
        PedidoProduto pedidoProduto3 = getPedidoProduto(3L, pedido3, produto3, 1);
        pedido3.setPedidosProdutos(List.of(pedidoProduto3));

        List<Pedido> pedidos = pedidoUseCases.ordenarListarPedidos(List.of(pedido, pedido2, pedido3));
        assertEquals(pedidos.get(0), pedido);assertEquals(pedidos.get(1), pedido2);assertEquals(pedidos.get(2), pedido3);
    }

    @Test
    public void testOrdenarListarPedidosPorEstado(){
        Produto produto = getProduto(1L, "LANCHE", "LANCHE", CategoriaProdutoEnum.LANCHE, 2.0);
        Pedido pedido = getPedido(1L, 1L, EstadoPedidoEnum.PREPARANDO, null);
        PedidoProduto pedidoProduto = getPedidoProduto(1L, pedido, produto, 1);
        pedido.setPedidosProdutos(List.of(pedidoProduto));


        Produto produto2 = getProduto(1L, "SOBREMESA", "SOBREMESA", CategoriaProdutoEnum.SOBREMESA, 2.0);
        Pedido pedido2 = getPedido(2L, 1L, EstadoPedidoEnum.RECEBIDO, null);
        PedidoProduto pedidoProduto2 = getPedidoProduto(2L, pedido2, produto2, 1);
        pedido2.setPedidosProdutos(List.of(pedidoProduto2));


        Produto produto3 = getProduto(1L, "BEBIDA", "BEBIDA", CategoriaProdutoEnum.BEBIDA, 2.0);
        Pedido pedido3 = getPedido(3L, 1L, EstadoPedidoEnum.PRONTO, null);
        PedidoProduto pedidoProduto3 = getPedidoProduto(3L, pedido3, produto3, 1);
        pedido3.setPedidosProdutos(List.of(pedidoProduto3));


        List<Pedido> pedidos = pedidoUseCases.ordenarListarPedidos(List.of(pedido, pedido2, pedido3));

        assertEquals(pedidos.get(0), pedido3);assertEquals(pedidos.get(1), pedido);assertEquals(pedidos.get(2), pedido2);
    }

    @Test
    public void testOrdenarListarPedidosPorEstadoEAntiguidade(){
        Produto produto = getProduto(1L, "LANCHE", "LANCHE", CategoriaProdutoEnum.LANCHE, 2.0);
        Pedido pedido = getPedido(1L, 1L, EstadoPedidoEnum.RECEBIDO, null);
        PedidoProduto pedidoProduto = getPedidoProduto(1L, pedido, produto, 1);
        pedido.setPedidosProdutos(List.of(pedidoProduto));


        Produto produto2 = getProduto(1L, "SOBREMESA", "SOBREMESA", CategoriaProdutoEnum.SOBREMESA, 2.0);
        Pedido pedido2 = getPedido(2L, 1L, EstadoPedidoEnum.PRONTO, null);
        PedidoProduto pedidoProduto2 = getPedidoProduto(2L, pedido2, produto2, 1);
        pedido2.setPedidosProdutos(List.of(pedidoProduto2));


        Produto produto3 = getProduto(1L, "BEBIDA", "BEBIDA", CategoriaProdutoEnum.BEBIDA, 2.0);
        Pedido pedido3 = getPedido(3L, 1L, EstadoPedidoEnum.RECEBIDO, null);
        PedidoProduto pedidoProduto3 = getPedidoProduto(3L, pedido3, produto3, 1);
        pedido3.setPedidosProdutos(List.of(pedidoProduto3));

        List<Pedido> pedidos = pedidoUseCases.ordenarListarPedidos(List.of(pedido, pedido2, pedido3));
        assertEquals(pedidos.get(0), pedido2);assertEquals(pedidos.get(1), pedido);assertEquals(pedidos.get(2), pedido3);
    }

    @Test
    public void testFiltrarPedidos(){
        Produto produto = getProduto(1L, "LANCHE", "LANCHE", CategoriaProdutoEnum.LANCHE, 2.0);
        Pedido pedido = getPedido(1L, 1L, EstadoPedidoEnum.RECEBIDO, null);
        PedidoProduto pedidoProduto = getPedidoProduto(1L, pedido, produto, 1);
        pedido.setPedidosProdutos(List.of(pedidoProduto));


        Produto produto2 = getProduto(1L, "SOBREMESA", "SOBREMESA", CategoriaProdutoEnum.SOBREMESA, 2.0);
        Pedido pedido2 = getPedido(2L, 1L, EstadoPedidoEnum.CANCELADO, null);
        PedidoProduto pedidoProduto2 = getPedidoProduto(2L, pedido2, produto2, 1);
        pedido2.setPedidosProdutos(List.of(pedidoProduto2));


        Produto produto3 = getProduto(1L, "BEBIDA", "BEBIDA", CategoriaProdutoEnum.BEBIDA, 2.0);
        Pedido pedido3 = getPedido(3L, 1L, EstadoPedidoEnum.FINALIZADO, null);
        PedidoProduto pedidoProduto3 = getPedidoProduto(3L, pedido3, produto3, 1);
        pedido3.setPedidosProdutos(List.of(pedidoProduto3));

        List<Pedido> pedidos = pedidoUseCases.filtrarPedidos(List.of(pedido, pedido2, pedido3), List.of(EstadoPedidoEnum.FINALIZADO, EstadoPedidoEnum.CANCELADO));
        assertEquals(pedidos.get(0), pedido);assertEquals(1, pedidos.size());
    }

    @Test
    public void teslistarPedidos(){
        Produto produto = getProduto(1L, "LANCHE", "LANCHE", CategoriaProdutoEnum.LANCHE, 2.0);
        Pedido pedido = getPedido(1L, 1L, EstadoPedidoEnum.RECEBIDO, null);
        PedidoProduto pedidoProduto = getPedidoProduto(1L, pedido, produto, 1);
        pedido.setPedidosProdutos(List.of(pedidoProduto));


        Produto produto2 = getProduto(1L, "SOBREMESA", "SOBREMESA", CategoriaProdutoEnum.SOBREMESA, 2.0);
        Pedido pedido2 = getPedido(2L, 1L, EstadoPedidoEnum.RECEBIDO, null);
        PedidoProduto pedidoProduto2 = getPedidoProduto(2L, pedido2, produto2, 1);
        pedido2.setPedidosProdutos(List.of(pedidoProduto2));


        Produto produto3 = getProduto(1L, "BEBIDA", "BEBIDA", CategoriaProdutoEnum.BEBIDA, 2.0);
        Pedido pedido3 = getPedido(3L, 1L, EstadoPedidoEnum.FINALIZADO, null);
        PedidoProduto pedidoProduto3 = getPedidoProduto(3L, pedido3, produto3, 1);
        pedido3.setPedidosProdutos(List.of(pedidoProduto3));

        Mockito.doReturn(List.of(pedido3, pedido, pedido2)).when(pedidoUseCases).consultaTodosOsPedidos();

        List<Pedido> pedidos = pedidoUseCases.listarPedidos();
        assertEquals(pedidos.get(0), pedido);assertEquals(pedidos.get(1), pedido2);assertEquals(2, pedidos.size());

    }

    private PedidoProduto getPedidoProduto(Long id, Pedido pedido, Produto produto, Integer quantidade) {
        return new PedidoProduto(id,  pedido,  produto, quantidade);
    }

    private Pedido getPedido(Long id, Long clienteId, EstadoPedidoEnum estado, List<PedidoProduto> pedidosProdutos) {
        return new Pedido(id, clienteId,  estado, null, pedidosProdutos);
    }

    private Produto getProduto(Long id, String nome, String descricao, CategoriaProdutoEnum categoria, Double preco) {
        return new Produto(id, nome, descricao, categoria, preco);
    }
}