package com.postech.application.usecases;

import com.postech.application.gateways.RepositorioDeProdutoGateway;
import com.postech.domain.entities.Produto;
import com.postech.domain.enums.CategoriaProdutoEnum;
import com.postech.domain.enums.ErroProdutoEnum;
import com.postech.domain.exceptions.ProdutoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProdutoUseCasesTest {

    @Mock
    private RepositorioDeProdutoGateway repositorioDeProduto;

    @InjectMocks
    private ProdutoUseCases produtoUseCases;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsultaPorIdSucesso() {
        Produto produto = new Produto(1L, "Produto A", "Descrição do Produto A", CategoriaProdutoEnum.LANCHE, 10.0);
        when(repositorioDeProduto.consultaProdutoPorId(1L)).thenReturn(produto);

        Produto resultado = produtoUseCases.consultaPorId(1L);

        assertEquals(produto, resultado);
        verify(repositorioDeProduto).consultaProdutoPorId(1L);
    }

    @Test
    public void testConsultaPorIdProdutoNaoEncontrado() {
        when(repositorioDeProduto.consultaProdutoPorId(1L)).thenReturn(null);

        ProdutoException exception = assertThrows(ProdutoException.class, () -> {
            produtoUseCases.consultaPorId(1L);
        });

        assertEquals(ErroProdutoEnum.PRODUTO_NAO_ENCONTRADO, exception.getErro());
        verify(repositorioDeProduto).consultaProdutoPorId(1L);
    }

    @Test
    public void testConsultaTodos() {
        Produto produto1 = new Produto(1L, "Produto A", "Descrição", CategoriaProdutoEnum.LANCHE, 10.0);
        when(repositorioDeProduto.consultaTodosProdutos(CategoriaProdutoEnum.LANCHE)).thenReturn(List.of(produto1));

        var produtos = produtoUseCases.consultaTodos("LANCHE");

        assertEquals(1, produtos.size());
        assertEquals(produto1, produtos.get(0));
        verify(repositorioDeProduto).consultaTodosProdutos(CategoriaProdutoEnum.LANCHE);
    }

    @Test
    public void testCriarProduto() {
        Produto produto = new Produto(null, "Produto A", "Descrição", CategoriaProdutoEnum.LANCHE, 10.0);
        Produto produtoSalvo = new Produto(1L, "Produto A", "Descrição", CategoriaProdutoEnum.LANCHE, 10.0);
        when(repositorioDeProduto.salvaProduto(produto)).thenReturn(produtoSalvo);

        Produto resultado = produtoUseCases.criarProduto(produto);

        assertEquals(produtoSalvo, resultado);
        verify(repositorioDeProduto).salvaProduto(produto);
    }

    @Test
    public void testAtualizaProdutoSucesso() {
        Produto produtoExistente = new Produto(1L, "Produto A", "Descrição", CategoriaProdutoEnum.LANCHE, 10.0);
        Produto novoProduto = new Produto(null, "Produto Atualizado", "Nova Descrição", CategoriaProdutoEnum.BEBIDA, 15.0);
        Produto produtoAtualizado = new Produto(1L, "Produto Atualizado", "Nova Descrição", CategoriaProdutoEnum.BEBIDA, 15.0);

        when(repositorioDeProduto.consultaProdutoPorId(1L)).thenReturn(produtoExistente);
        when(repositorioDeProduto.salvaProduto(produtoExistente)).thenReturn(produtoAtualizado);

        Produto resultado = produtoUseCases.atualizaProduto(1L, novoProduto);

        assertEquals(produtoAtualizado, resultado);
        verify(repositorioDeProduto).consultaProdutoPorId(1L);
        verify(repositorioDeProduto).salvaProduto(produtoExistente);
    }

    @Test
    public void testAtualizaProdutoProdutoNaoEncontrado() {
        Produto novoProduto = new Produto(null, "Produto Atualizado", "Nova Descrição", CategoriaProdutoEnum.BEBIDA, 15.0);
        when(repositorioDeProduto.consultaProdutoPorId(1L)).thenReturn(null);

        ProdutoException exception = assertThrows(ProdutoException.class, () -> {
            produtoUseCases.atualizaProduto(1L, novoProduto);
        });
        assertEquals(ErroProdutoEnum.PRODUTO_NAO_ENCONTRADO, exception.getErro());
        verify(repositorioDeProduto).consultaProdutoPorId(1L);
    }

    @Test
    public void testDeletaPorIdSucesso() {
        Produto produto = new Produto(1L, "Produto A", "Descrição", CategoriaProdutoEnum.LANCHE, 10.0);
        when(repositorioDeProduto.consultaProdutoPorId(1L)).thenReturn(produto);

        produtoUseCases.deletaPorId(1L);

        verify(repositorioDeProduto).consultaProdutoPorId(1L);
        verify(repositorioDeProduto).deletaProdutoPorId(1L);
    }

    @Test
    public void testDeletaPorIdProdutoNaoEncontrado() {
        when(repositorioDeProduto.consultaProdutoPorId(1L)).thenReturn(null);

        ProdutoException exception = assertThrows(ProdutoException.class, () -> {
            produtoUseCases.deletaPorId(1L);
        });
        assertEquals(ErroProdutoEnum.PRODUTO_NAO_ENCONTRADO, exception.getErro());
        verify(repositorioDeProduto).consultaProdutoPorId(1L);
    }

    @Test
    public void testDeletaPorIdProdutoReferenciadoEmPedido() {
        Produto produto = new Produto(1L, "Produto A", "Descrição", CategoriaProdutoEnum.LANCHE, 10.0);
        when(repositorioDeProduto.consultaProdutoPorId(1L)).thenReturn(produto);
        doThrow(new ProdutoException(ErroProdutoEnum.PRODUTO_REFERENCIADO_EM_PEDIDO))
                .when(repositorioDeProduto).deletaProdutoPorId(1L);

        ProdutoException exception = assertThrows(ProdutoException.class, () -> {
            produtoUseCases.deletaPorId(1L);
        });

        assertEquals(ErroProdutoEnum.PRODUTO_REFERENCIADO_EM_PEDIDO, exception.getErro());
        verify(repositorioDeProduto).consultaProdutoPorId(1L);
        verify(repositorioDeProduto).deletaProdutoPorId(1L);
    }
}
