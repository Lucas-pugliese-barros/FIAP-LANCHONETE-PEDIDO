import com.postech.domain.entities.Pedido;
import com.postech.domain.enums.EstadoPedidoEnum;
import com.postech.infra.gateways.RepositorioDePedidoGatewayImpl;
import com.postech.infra.mappers.PedidoMapper;
import com.postech.infra.persistence.entities.PedidoEntity;
import com.postech.infra.persistence.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RepositorioDePedidoGatewayTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private RepositorioDePedidoGatewayImpl repositorioDePedidoGateway;

    private Pedido pedido;
    private PedidoEntity pedidoEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        pedido = new Pedido(1L, 123L, EstadoPedidoEnum.RECEBIDO, null, null);
        pedidoEntity = new PedidoEntity(1L, 123L, EstadoPedidoEnum.RECEBIDO, null, null);
    }

    @Test
    public void testConsultaPedidoPorId_found() {
        when(pedidoRepository.getPedidoEntityById(1L)).thenReturn(Optional.of(pedidoEntity));
        when(pedidoMapper.paraDominio(pedidoEntity)).thenReturn(pedido);

        Pedido result = repositorioDePedidoGateway.consultaPedidoPorId(1L);

        assertNotNull(result);
        assertEquals(pedido.getId(), result.getId());
        verify(pedidoRepository, times(1)).getPedidoEntityById(1L);
    }

    @Test
    public void testConsultaPedidoPorId_notFound() {
        when(pedidoRepository.getPedidoEntityById(1L)).thenReturn(Optional.empty());

        Pedido result = repositorioDePedidoGateway.consultaPedidoPorId(1L);

        assertNull(result);
        verify(pedidoRepository, times(1)).getPedidoEntityById(1L);
    }

    @Test
    public void testSalvaPedido() {
        when(pedidoMapper.paraEntidade(pedido)).thenReturn(pedidoEntity);
        when(pedidoRepository.save(pedidoEntity)).thenReturn(pedidoEntity);
        when(pedidoMapper.paraDominio(pedidoEntity)).thenReturn(pedido);

        Pedido result = repositorioDePedidoGateway.salvaPedido(pedido);

        assertNotNull(result);
        assertEquals(pedido.getId(), result.getId());
        verify(pedidoRepository, times(1)).save(pedidoEntity);
    }

    @Test
    public void testDeletaPedido() {
        repositorioDePedidoGateway.deletaPedido(1L);

        verify(pedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testBuscaTodosPedidos() {
        List<PedidoEntity> pedidoEntities = List.of(pedidoEntity);
        when(pedidoRepository.findAll()).thenReturn(pedidoEntities);
        when(pedidoMapper.paraDominio(pedidoEntity)).thenReturn(pedido);

        List<Pedido> result = repositorioDePedidoGateway.buscaTodosPedidos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(pedido.getId(), result.get(0).getId());
        verify(pedidoRepository, times(1)).findAll();
    }
}
