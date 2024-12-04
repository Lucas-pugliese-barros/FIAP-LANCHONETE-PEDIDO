import com.postech.application.usecases.PedidoUseCases;
import com.postech.domain.entities.Pedido;
import com.postech.domain.enums.EstadoPedidoEnum;
import com.postech.infra.controller.PedidoController;
import com.postech.infra.dto.request.PedidoRequestDTO;
import com.postech.infra.dto.response.CheckoutResponseDTO;
import com.postech.infra.dto.response.PedidoResponseDTO;
import com.postech.infra.mappers.PedidoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoUseCases pedidoUseCases;

    @Mock
    private PedidoMapper pedidoMapper;

    private PedidoRequestDTO pedidoRequestDTO;
    private Pedido pedido;
    private CheckoutResponseDTO checkoutResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pedidoRequestDTO = new PedidoRequestDTO();
        pedidoRequestDTO.setClienteId(1L);
        pedidoRequestDTO.setPedidosProdutos(Collections.emptyList());

        pedido = new Pedido(1L, 1L, EstadoPedidoEnum.RECEBIDO, null, Collections.emptyList());
        checkoutResponseDTO = new CheckoutResponseDTO("Pedido realizado e enviado para a fila da cozinha", null);
    }

    @Test
    void testCriarPedido() {
        when(pedidoUseCases.criaPedido(pedidoRequestDTO)).thenReturn(pedido);
        when(pedidoUseCases.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoMapper.paraResponseDto(pedido)).thenReturn(new PedidoResponseDTO(null,null,null,null, null));

        ResponseEntity<Object> response = pedidoController.criarPedido(pedidoRequestDTO);

        assertEquals(201, response.getStatusCodeValue());
        verify(pedidoUseCases, times(1)).criaPedido(pedidoRequestDTO);
        verify(pedidoUseCases, times(1)).salvarPedido(pedido);
    }

    @Test
    void testConsultarPedidoPorId() {
        Long pedidoId = 1L;
        when(pedidoUseCases.consultaPorId(pedidoId)).thenReturn(pedido);
        when(pedidoMapper.paraResponseDto(pedido)).thenReturn(new PedidoResponseDTO(null,null,null,null, null));

        ResponseEntity<Object> response = pedidoController.consultarPedidoPorId(pedidoId);

        assertEquals(200, response.getStatusCodeValue());
        verify(pedidoUseCases, times(1)).consultaPorId(pedidoId);
    }

    @Test
    void testNotificarPedidoEstado() {
        Long pedidoId = 1L;
        when(pedidoUseCases.notificaEstado(pedidoId)).thenReturn(pedido);

        ResponseEntity<Object> response = pedidoController.notificarPedidoEstado(pedidoId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pedido.getEstado(), response.getBody());
        verify(pedidoUseCases, times(1)).notificaEstado(pedidoId);
    }

    @Test
    void testListarPedidos() {
        when(pedidoUseCases.listarPedidos()).thenReturn(Collections.singletonList(pedido));
        when(pedidoMapper.paraDTOLista(Collections.singletonList(pedido))).thenReturn(new ArrayList<>());

        ResponseEntity<Object> response = pedidoController.listarPedidos();

        assertEquals(200, response.getStatusCodeValue());
        verify(pedidoUseCases, times(1)).listarPedidos();
    }

    @Test
    void testAtualizarEstadoPedidoPorId() {
        Long pedidoId = 1L;
        EstadoPedidoEnum novoEstado = EstadoPedidoEnum.PAGO;
        when(pedidoUseCases.atualizaEstadoPorIdDoPedido(pedidoId, novoEstado)).thenReturn(pedido);
        when(pedidoMapper.paraResponseDto(pedido)).thenReturn(new PedidoResponseDTO(null,null,null,null, null));

        ResponseEntity<Object> response = pedidoController.atualizarEstadoPedidoPorId(pedidoId, novoEstado);

        assertEquals(200, response.getStatusCodeValue());
        verify(pedidoUseCases, times(1)).atualizaEstadoPorIdDoPedido(pedidoId, novoEstado);
    }

    @Test
    void testCheckout() {
        Long pedidoId = 1L;
        when(pedidoUseCases.checkout(pedidoId)).thenReturn(pedido);
        when(pedidoMapper.paraResponseDto(pedido)).thenReturn(new PedidoResponseDTO(null,null,null,null, null));

        ResponseEntity<Object> response = pedidoController.checkout(pedidoId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof CheckoutResponseDTO);
        verify(pedidoUseCases, times(1)).checkout(pedidoId);
    }

    @Test
    void testDeletarPedido() {
        Long pedidoId = 1L;

        ResponseEntity<Object> response = pedidoController.deletarPedido(pedidoId);

        assertEquals(204, response.getStatusCodeValue());
        verify(pedidoUseCases, times(1)).deleta(pedidoId);
    }
}
