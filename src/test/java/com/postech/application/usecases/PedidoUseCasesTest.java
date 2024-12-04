import com.postech.application.client.PaymentClient;
import com.postech.application.gateways.RepositorioDePedidoGateway;
import com.postech.application.usecases.PedidoUseCases;
import com.postech.application.usecases.ProdutoUseCases;
import com.postech.domain.entities.Pedido;
import com.postech.domain.entities.PedidoProduto;
import com.postech.domain.entities.Produto;
import com.postech.domain.enums.CategoriaProdutoEnum;
import com.postech.domain.enums.EstadoPedidoEnum;
import com.postech.domain.exceptions.DominioException;
import com.postech.domain.exceptions.PedidoException;
import com.postech.infra.dto.request.PedidoProdutoRequestDTO;
import com.postech.infra.dto.request.PedidoRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoUseCasesTest {

    @Mock
    private RepositorioDePedidoGateway repositorioDePedido;

    @Mock
    private ProdutoUseCases produtoUseCases;

    @Mock
    private PaymentClient paymentClient;

    @InjectMocks
    private PedidoUseCases pedidoUseCases;

    private Pedido pedido;
    private PedidoRequestDTO pedidoRequestDTO;
    private PedidoProdutoRequestDTO pedidoProdutoRequestDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Produto produto = new Produto(1L, "nome", "descricao", CategoriaProdutoEnum.LANCHE, 20.0);

        PedidoProduto pedidoProduto = new PedidoProduto();
        pedidoProduto.setProduto(produto);
        pedidoProduto.setQuantidade(10);

        pedido = new Pedido(1L, 1L, EstadoPedidoEnum.RECEBIDO, null, List.of(pedidoProduto));

        pedidoProdutoRequestDTO = new PedidoProdutoRequestDTO();
        pedidoProdutoRequestDTO.setProdutoId(1L);
        pedidoProdutoRequestDTO.setQuantidade(1);

        pedidoRequestDTO = new PedidoRequestDTO();
        pedidoRequestDTO.setClienteId(1L);
        pedidoRequestDTO.setPedidosProdutos(List.of(pedidoProdutoRequestDTO));
    }

    @Test
    public void testConsultaPorId_found() {
        when(repositorioDePedido.consultaPedidoPorId(1L)).thenReturn(pedido);

        Pedido result = pedidoUseCases.consultaPorId(1L);

        assertNotNull(result);
        assertEquals(pedido.getId(), result.getId());
        verify(repositorioDePedido, times(1)).consultaPedidoPorId(1L);
    }

    @Test
    public void testConsultaPorId_notFound() {
        when(repositorioDePedido.consultaPedidoPorId(1L)).thenReturn(null);

        PedidoException thrown = assertThrows(PedidoException.class, () -> pedidoUseCases.consultaPorId(1L));
        assertNull(thrown.getMessage());
    }

    @Test
    public void testAtualizaEstadoPorIdDoPedido() {
        Pedido pedidoAtualizado = new Pedido(1L, 1L, EstadoPedidoEnum.PAGO, LocalDate.now(), null);
        when(repositorioDePedido.consultaPedidoPorId(1L)).thenReturn(pedido);
        when(repositorioDePedido.salvaPedido(any(Pedido.class))).thenReturn(pedidoAtualizado);

        Pedido result = pedidoUseCases.atualizaEstadoPorIdDoPedido(1L, EstadoPedidoEnum.PAGO);

        assertNotNull(result);
        assertEquals(EstadoPedidoEnum.PAGO, result.getEstado());
        assertEquals(pedidoAtualizado.getDataDoPagamento(), result.getDataDoPagamento());
        verify(repositorioDePedido, times(1)).salvaPedido(any(Pedido.class));
    }

    @Test
    public void testAtualizaEstadoPorIdDoPedido_estadoInvalido() {
        when(repositorioDePedido.consultaPedidoPorId(1L)).thenReturn(pedido);

        PedidoException thrown = assertThrows(PedidoException.class, () -> pedidoUseCases.atualizaEstadoPorIdDoPedido(1L, EstadoPedidoEnum.FINALIZADO));
        assertNull(thrown.getMessage());
    }

    @Test
    public void testNotificaEstado() {
        when(repositorioDePedido.consultaPedidoPorId(1L)).thenReturn(pedido);

        Pedido result = pedidoUseCases.notificaEstado(1L);

        assertNotNull(result);
        verify(repositorioDePedido, times(1)).consultaPedidoPorId(1L);
    }

    @Test
    public void testConsultaTodosOsPedidos() {
        when(repositorioDePedido.buscaTodosPedidos()).thenReturn(List.of(pedido));

        List<Pedido> result = pedidoUseCases.consultaTodosOsPedidos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repositorioDePedido, times(1)).buscaTodosPedidos();
    }

    @Test
    public void testConsultaTodosOsPedidos_emptyList() {
        when(repositorioDePedido.buscaTodosPedidos()).thenReturn(List.of());

        PedidoException thrown = assertThrows(PedidoException.class, () -> pedidoUseCases.consultaTodosOsPedidos());
        assertNull(thrown.getMessage());
    }

    @Test
    public void testDeleta() {
        pedidoUseCases.deleta(1L);

        verify(repositorioDePedido, times(1)).deletaPedido(1L);
    }

    @Test
    public void testSalvarPedido() {
        Pedido pedidoFinal = new Pedido(1L, 1L, EstadoPedidoEnum.RECEBIDO, null, List.of(new PedidoProduto()));
        when(repositorioDePedido.salvaPedido(any(Pedido.class))).thenReturn(pedidoFinal);
        doNothing().when(paymentClient).enviarPagamento(any(), any(), any());

        Pedido result = pedidoUseCases.salvarPedido(pedido);

        assertNotNull(result);
        verify(repositorioDePedido, times(2)).salvaPedido(any(Pedido.class));
        verify(paymentClient, times(1)).enviarPagamento(anyLong(), anyLong(), any(BigDecimal.class));
    }

    @Test
    public void testSalvarPedido_paymentFailure() {
        Pedido pedidoFinal = new Pedido(1L, 1L, EstadoPedidoEnum.RECEBIDO, null, List.of(new PedidoProduto()));
        when(repositorioDePedido.salvaPedido(any(Pedido.class))).thenReturn(pedidoFinal);
        doThrow(new DominioException("Error ao tentar enviar pagamento")).when(paymentClient).enviarPagamento(anyLong(), anyLong(), any(BigDecimal.class));

        DominioException thrown = assertThrows(DominioException.class, () -> pedidoUseCases.salvarPedido(pedido));
        assertEquals("Error ao tentar enviar pagamento", thrown.getMessage());
    }

    @Test
    public void testCriaPedido() {
        when(produtoUseCases.consultaPorId(1L)).thenReturn(null);

        Pedido result = pedidoUseCases.criaPedido(pedidoRequestDTO);

        assertNotNull(result);
        assertEquals(pedidoRequestDTO.getClienteId(), result.getClienteId());
        assertEquals(EstadoPedidoEnum.RECEBIDO, result.getEstado());
    }
}
