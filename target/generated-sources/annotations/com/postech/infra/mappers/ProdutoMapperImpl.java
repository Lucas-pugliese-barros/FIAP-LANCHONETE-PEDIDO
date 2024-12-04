package com.postech.infra.mappers;

import com.postech.domain.entities.Produto;
import com.postech.domain.enums.CategoriaProdutoEnum;
import com.postech.infra.dto.request.ProdutoRequestDTO;
import com.postech.infra.persistence.entities.ProdutoEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T21:10:17-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Azul Systems, Inc.)"
)
@Component
public class ProdutoMapperImpl implements ProdutoMapper {

    @Override
    public ProdutoEntity paraEntidade(Produto produto) {
        if ( produto == null ) {
            return null;
        }

        ProdutoEntity produtoEntity = new ProdutoEntity();

        produtoEntity.setId( produto.getId() );
        produtoEntity.setNome( produto.getNome() );
        produtoEntity.setDescricao( produto.getDescricao() );
        produtoEntity.setCategoria( produto.getCategoria() );
        produtoEntity.setPreco( produto.getPreco() );

        return produtoEntity;
    }

    @Override
    public Produto paraDominio(ProdutoEntity produtoEntity) {
        if ( produtoEntity == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String descricao = null;
        CategoriaProdutoEnum categoria = null;
        Double preco = null;

        id = produtoEntity.getId();
        nome = produtoEntity.getNome();
        descricao = produtoEntity.getDescricao();
        categoria = produtoEntity.getCategoria();
        preco = produtoEntity.getPreco();

        Produto produto = new Produto( id, nome, descricao, categoria, preco );

        return produto;
    }

    @Override
    public Produto paraDominio(ProdutoRequestDTO produtoRequestDTO) {
        if ( produtoRequestDTO == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String descricao = null;
        CategoriaProdutoEnum categoria = null;
        Double preco = null;

        id = produtoRequestDTO.getId();
        nome = produtoRequestDTO.getNome();
        descricao = produtoRequestDTO.getDescricao();
        categoria = produtoRequestDTO.getCategoria();
        preco = produtoRequestDTO.getPreco();

        Produto produto = new Produto( id, nome, descricao, categoria, preco );

        return produto;
    }

    @Override
    public Produto paraDominio(Produto produto) {
        if ( produto == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String descricao = null;
        CategoriaProdutoEnum categoria = null;
        Double preco = null;

        id = produto.getId();
        nome = produto.getNome();
        descricao = produto.getDescricao();
        categoria = produto.getCategoria();
        preco = produto.getPreco();

        Produto produto1 = new Produto( id, nome, descricao, categoria, preco );

        return produto1;
    }

    @Override
    public ProdutoRequestDTO paraDTO(Produto produto) {
        if ( produto == null ) {
            return null;
        }

        ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO();

        produtoRequestDTO.setId( produto.getId() );
        produtoRequestDTO.setNome( produto.getNome() );
        produtoRequestDTO.setDescricao( produto.getDescricao() );
        produtoRequestDTO.setCategoria( produto.getCategoria() );
        produtoRequestDTO.setPreco( produto.getPreco() );

        return produtoRequestDTO;
    }

    @Override
    public List<ProdutoEntity> paraEntidadeLista(List<Produto> produtos) {
        if ( produtos == null ) {
            return null;
        }

        List<ProdutoEntity> list = new ArrayList<ProdutoEntity>( produtos.size() );
        for ( Produto produto : produtos ) {
            list.add( paraEntidade( produto ) );
        }

        return list;
    }

    @Override
    public List<Produto> paraDominioLista(List<ProdutoEntity> produtosEntidade) {
        if ( produtosEntidade == null ) {
            return null;
        }

        List<Produto> list = new ArrayList<Produto>( produtosEntidade.size() );
        for ( ProdutoEntity produtoEntity : produtosEntidade ) {
            list.add( paraDominio( produtoEntity ) );
        }

        return list;
    }

    @Override
    public List<Produto> paraDominioListaDTO(List<ProdutoRequestDTO> produtosDTO) {
        if ( produtosDTO == null ) {
            return null;
        }

        List<Produto> list = new ArrayList<Produto>( produtosDTO.size() );
        for ( ProdutoRequestDTO produtoRequestDTO : produtosDTO ) {
            list.add( paraDominio( produtoRequestDTO ) );
        }

        return list;
    }

    @Override
    public List<ProdutoRequestDTO> paraDTOListaDominio(List<Produto> produtos) {
        if ( produtos == null ) {
            return null;
        }

        List<ProdutoRequestDTO> list = new ArrayList<ProdutoRequestDTO>( produtos.size() );
        for ( Produto produto : produtos ) {
            list.add( paraDTO( produto ) );
        }

        return list;
    }
}
