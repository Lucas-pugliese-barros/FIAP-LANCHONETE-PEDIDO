import com.postech.domain.entities.Produto;
import com.postech.domain.enums.CategoriaProdutoEnum;
import com.postech.infra.gateways.RepositorioDeProdutoGatewayImpl;
import com.postech.infra.mappers.ProdutoMapper;
import com.postech.infra.persistence.entities.ProdutoEntity;
import com.postech.infra.persistence.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RepositorioDeProdutoGatewayTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private RepositorioDeProdutoGatewayImpl repositorioDeProdutoGateway;

    private Produto produto;
    private ProdutoEntity produtoEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        produto = new Produto(1L, "Produto Teste", "desc",CategoriaProdutoEnum.LANCHE, 100.0);
        produtoEntity = new ProdutoEntity(1L, "Produto Teste", "desc", CategoriaProdutoEnum.LANCHE, 100.0);
    }

    @Test
    public void testConsultaProdutoPorId_found() {
        when(produtoRepository.getProdutoEntityById(1L)).thenReturn(Optional.of(produtoEntity));
        when(produtoMapper.paraDominio(produtoEntity)).thenReturn(produto);

        Produto result = repositorioDeProdutoGateway.consultaProdutoPorId(1L);

        assertNotNull(result);
        assertEquals(produto.getId(), result.getId());
        assertEquals(produto.getNome(), result.getNome());
        verify(produtoRepository, times(1)).getProdutoEntityById(1L);
    }

    @Test
    public void testConsultaProdutoPorId_notFound() {
        when(produtoRepository.getProdutoEntityById(1L)).thenReturn(Optional.empty());

        Produto result = repositorioDeProdutoGateway.consultaProdutoPorId(1L);

        assertNull(result);
        verify(produtoRepository, times(1)).getProdutoEntityById(1L);
    }

    @Test
    public void testConsultaTodosProdutos() {
        List<ProdutoEntity> produtoEntities = List.of(produtoEntity);
        when(produtoRepository.getProdutoEntity(CategoriaProdutoEnum.LANCHE)).thenReturn(Optional.of(produtoEntities));
        when(produtoMapper.paraDominioLista(produtoEntities)).thenReturn(List.of(produto));

        List<Produto> result = repositorioDeProdutoGateway.consultaTodosProdutos(CategoriaProdutoEnum.LANCHE);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(produto.getId(), result.get(0).getId());
        verify(produtoRepository, times(1)).getProdutoEntity(CategoriaProdutoEnum.LANCHE);
    }

    @Test
    public void testConsultaProdutosPorCategoria() {
        List<ProdutoEntity> produtoEntities = List.of(produtoEntity);
        when(produtoRepository.getProdutoEntityByCategoria(CategoriaProdutoEnum.LANCHE)).thenReturn(Optional.of(produtoEntities));
        when(produtoMapper.paraDominioLista(produtoEntities)).thenReturn(List.of(produto));

        List<Produto> result = repositorioDeProdutoGateway.consultaProdutosPorCategoria(CategoriaProdutoEnum.LANCHE);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(produto.getId(), result.get(0).getId());
        verify(produtoRepository, times(1)).getProdutoEntityByCategoria(CategoriaProdutoEnum.LANCHE);
    }

    @Test
    public void testSalvaProduto() {
        when(produtoMapper.paraEntidade(produto)).thenReturn(produtoEntity);
        when(produtoRepository.save(produtoEntity)).thenReturn(produtoEntity);
        when(produtoMapper.paraDominio(produtoEntity)).thenReturn(produto);

        Produto result = repositorioDeProdutoGateway.salvaProduto(produto);

        assertNotNull(result);
        assertEquals(produto.getId(), result.getId());
        assertEquals(produto.getNome(), result.getNome());
        verify(produtoRepository, times(1)).save(produtoEntity);
    }

    @Test
    public void testDeletaProdutoPorId() {
        repositorioDeProdutoGateway.deletaProdutoPorId(1L);

        verify(produtoRepository, times(1)).deleteById(1L);
    }
}
