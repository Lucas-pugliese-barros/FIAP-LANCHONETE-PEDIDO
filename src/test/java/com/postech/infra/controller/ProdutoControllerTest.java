import com.postech.application.usecases.ProdutoUseCases;
import com.postech.domain.entities.Produto;
import com.postech.domain.enums.CategoriaProdutoEnum;
import com.postech.infra.controller.ProdutoController;
import com.postech.infra.dto.request.ProdutoRequestDTO;
import com.postech.infra.mappers.ProdutoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoUseCases produtoUseCases;

    @Mock
    private ProdutoMapper produtoMapper;

    private ProdutoRequestDTO produtoRequestDTO;
    private Produto produto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        produtoRequestDTO = new ProdutoRequestDTO();
        produtoRequestDTO.setNome("Produto 1");
        produtoRequestDTO.setCategoria(CategoriaProdutoEnum.LANCHE);
        produtoRequestDTO.setPreco(100.0);

        produto = new Produto(1L, "Produto Teste", "desc",CategoriaProdutoEnum.LANCHE, 100.0);
    }

    @Test
    void testConsultaProdutoPorId() {
        Long produtoId = 1L;
        when(produtoUseCases.consultaPorId(produtoId)).thenReturn(produto);
        when(produtoMapper.paraDTO(produto)).thenReturn(new ProdutoRequestDTO());

        ResponseEntity<Object> response = produtoController.consultaProdutoPorID(produtoId);

        assertEquals(200, response.getStatusCodeValue());
        verify(produtoUseCases, times(1)).consultaPorId(produtoId);
    }

    @Test
    void testCriarProduto() {
        when(produtoMapper.paraDominio(produtoRequestDTO)).thenReturn(produto);
        when(produtoUseCases.criarProduto(produto)).thenReturn(produto);
        when(produtoMapper.paraDTO(produto)).thenReturn(new ProdutoRequestDTO());

        ResponseEntity<Object> response = produtoController.criarProduto(produtoRequestDTO);

        assertEquals(201, response.getStatusCodeValue());
        verify(produtoUseCases, times(1)).criarProduto(produto);
    }

    @Test
    void testAtualizarProduto() {
        Long produtoId = 1L;
        when(produtoMapper.paraDominio(produtoRequestDTO)).thenReturn(produto);
        when(produtoUseCases.atualizaProduto(produtoId, produto)).thenReturn(produto);
        when(produtoMapper.paraDTO(produto)).thenReturn(new ProdutoRequestDTO());

        ResponseEntity<Object> response = produtoController.atualizarProduto(produtoId, produtoRequestDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(produtoUseCases, times(1)).atualizaProduto(produtoId, produto);
    }

    @Test
    void testListarTodosProdutos() {
        String categoria = "LANCHE";
        when(produtoUseCases.consultaTodos(categoria)).thenReturn(Collections.singletonList(produto));
        when(produtoMapper.paraDTOListaDominio(Collections.singletonList(produto))).thenReturn(new ArrayList<>());

        ResponseEntity<Object> response = produtoController.listarTodosProdutos(categoria);

        assertEquals(200, response.getStatusCodeValue());
        verify(produtoUseCases, times(1)).consultaTodos(categoria);
    }

    @Test
    void testConsultaTodosPorCategoria() {
        CategoriaProdutoEnum categoriaEnum = CategoriaProdutoEnum.LANCHE;
        when(produtoUseCases.consultaPorCategoria(categoriaEnum)).thenReturn(Collections.singletonList(produto));
        when(produtoMapper.paraDTOListaDominio(Collections.singletonList(produto))).thenReturn(new ArrayList<>());

        ResponseEntity<Object> response = produtoController.consultaTodosPorCategoria(categoriaEnum);

        assertEquals(200, response.getStatusCodeValue());
        verify(produtoUseCases, times(1)).consultaPorCategoria(categoriaEnum);
    }

    @Test
    void testDeletarProdutoPorId() {
        Long produtoId = 1L;

        ResponseEntity<Object> response = produtoController.deletarProdutoPorId(produtoId);

        assertEquals(204, response.getStatusCodeValue());
        verify(produtoUseCases, times(1)).deletaPorId(produtoId);
    }
}
