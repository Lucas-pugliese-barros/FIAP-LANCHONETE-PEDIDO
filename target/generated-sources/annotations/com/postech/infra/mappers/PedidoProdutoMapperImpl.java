package com.postech.infra.mappers;

import com.postech.domain.entities.Pedido;
import com.postech.domain.entities.PedidoProduto;
import com.postech.domain.entities.Produto;
import com.postech.domain.enums.CategoriaProdutoEnum;
import com.postech.domain.enums.EstadoPedidoEnum;
import com.postech.infra.dto.request.ProdutoRequestDTO;
import com.postech.infra.dto.response.PedidoProdutoResponseDTO;
import com.postech.infra.dto.response.PedidoResponseDTO;
import com.postech.infra.persistence.entities.PedidoEntity;
import com.postech.infra.persistence.entities.PedidoProdutoEntity;
import com.postech.infra.persistence.entities.ProdutoEntity;
import java.time.LocalDate;
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
public class PedidoProdutoMapperImpl implements PedidoProdutoMapper {

    @Override
    public PedidoProdutoEntity paraEntidade(PedidoProduto pedidoProduto) {
        if ( pedidoProduto == null ) {
            return null;
        }

        PedidoProdutoEntity pedidoProdutoEntity = new PedidoProdutoEntity();

        pedidoProdutoEntity.setId( pedidoProduto.getId() );
        pedidoProdutoEntity.setPedido( pedidoToPedidoEntity( pedidoProduto.getPedido() ) );
        pedidoProdutoEntity.setProduto( produtoToProdutoEntity( pedidoProduto.getProduto() ) );
        pedidoProdutoEntity.setQuantidade( pedidoProduto.getQuantidade() );

        return pedidoProdutoEntity;
    }

    @Override
    public PedidoProduto paraDominio(PedidoProdutoEntity pedidoProdutoEntity) {
        if ( pedidoProdutoEntity == null ) {
            return null;
        }

        PedidoProduto pedidoProduto = new PedidoProduto();

        pedidoProduto.setId( pedidoProdutoEntity.getId() );
        pedidoProduto.setPedido( pedidoEntityToPedido( pedidoProdutoEntity.getPedido() ) );
        pedidoProduto.setProduto( produtoEntityToProduto( pedidoProdutoEntity.getProduto() ) );
        pedidoProduto.setQuantidade( pedidoProdutoEntity.getQuantidade() );

        return pedidoProduto;
    }

    @Override
    public PedidoProduto paraDominio(PedidoProdutoResponseDTO pedidoProdutoResponseDTO) {
        if ( pedidoProdutoResponseDTO == null ) {
            return null;
        }

        PedidoProduto pedidoProduto = new PedidoProduto();

        pedidoProduto.setId( pedidoProdutoResponseDTO.getId() );
        pedidoProduto.setPedido( pedidoResponseDTOToPedido( pedidoProdutoResponseDTO.getPedido() ) );
        pedidoProduto.setProduto( produtoRequestDTOToProduto( pedidoProdutoResponseDTO.getProduto() ) );
        pedidoProduto.setQuantidade( pedidoProdutoResponseDTO.getQuantidade() );

        return pedidoProduto;
    }

    @Override
    public PedidoProdutoResponseDTO paraDTO(PedidoProduto pedidoProduto) {
        if ( pedidoProduto == null ) {
            return null;
        }

        PedidoProdutoResponseDTO pedidoProdutoResponseDTO = new PedidoProdutoResponseDTO();

        pedidoProdutoResponseDTO.setId( pedidoProduto.getId() );
        pedidoProdutoResponseDTO.setPedido( pedidoToPedidoResponseDTO( pedidoProduto.getPedido() ) );
        pedidoProdutoResponseDTO.setProduto( produtoToProdutoRequestDTO( pedidoProduto.getProduto() ) );
        if ( pedidoProduto.getQuantidade() != null ) {
            pedidoProdutoResponseDTO.setQuantidade( pedidoProduto.getQuantidade() );
        }

        return pedidoProdutoResponseDTO;
    }

    @Override
    public List<PedidoProdutoEntity> paraEntidadeLista(List<PedidoProduto> pedidosProdutos) {
        if ( pedidosProdutos == null ) {
            return null;
        }

        List<PedidoProdutoEntity> list = new ArrayList<PedidoProdutoEntity>( pedidosProdutos.size() );
        for ( PedidoProduto pedidoProduto : pedidosProdutos ) {
            list.add( paraEntidade( pedidoProduto ) );
        }

        return list;
    }

    @Override
    public List<PedidoProduto> paraDominioListaEntidade(List<PedidoProdutoEntity> pedidosProdutosEntidade) {
        if ( pedidosProdutosEntidade == null ) {
            return null;
        }

        List<PedidoProduto> list = new ArrayList<PedidoProduto>( pedidosProdutosEntidade.size() );
        for ( PedidoProdutoEntity pedidoProdutoEntity : pedidosProdutosEntidade ) {
            list.add( paraDominio( pedidoProdutoEntity ) );
        }

        return list;
    }

    @Override
    public List<PedidoProduto> paraDominioListaDTO(List<PedidoProdutoResponseDTO> pedidosProdutosDTO) {
        if ( pedidosProdutosDTO == null ) {
            return null;
        }

        List<PedidoProduto> list = new ArrayList<PedidoProduto>( pedidosProdutosDTO.size() );
        for ( PedidoProdutoResponseDTO pedidoProdutoResponseDTO : pedidosProdutosDTO ) {
            list.add( paraDominio( pedidoProdutoResponseDTO ) );
        }

        return list;
    }

    @Override
    public List<PedidoProdutoResponseDTO> paraDTOLista(List<PedidoProduto> pedidosProdutos) {
        if ( pedidosProdutos == null ) {
            return null;
        }

        List<PedidoProdutoResponseDTO> list = new ArrayList<PedidoProdutoResponseDTO>( pedidosProdutos.size() );
        for ( PedidoProduto pedidoProduto : pedidosProdutos ) {
            list.add( paraDTO( pedidoProduto ) );
        }

        return list;
    }

    protected PedidoEntity pedidoToPedidoEntity(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        PedidoEntity pedidoEntity = new PedidoEntity();

        pedidoEntity.setId( pedido.getId() );
        pedidoEntity.setClienteId( pedido.getClienteId() );
        pedidoEntity.setEstado( pedido.getEstado() );
        pedidoEntity.setDataDoPagamento( pedido.getDataDoPagamento() );

        return pedidoEntity;
    }

    protected ProdutoEntity produtoToProdutoEntity(Produto produto) {
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

    protected Pedido pedidoEntityToPedido(PedidoEntity pedidoEntity) {
        if ( pedidoEntity == null ) {
            return null;
        }

        Long id = null;
        Long clienteId = null;
        EstadoPedidoEnum estado = null;
        LocalDate dataDoPagamento = null;

        id = pedidoEntity.getId();
        clienteId = pedidoEntity.getClienteId();
        estado = pedidoEntity.getEstado();
        dataDoPagamento = pedidoEntity.getDataDoPagamento();

        List<PedidoProduto> pedidosProdutos = null;

        Pedido pedido = new Pedido( id, clienteId, estado, dataDoPagamento, pedidosProdutos );

        return pedido;
    }

    protected Produto produtoEntityToProduto(ProdutoEntity produtoEntity) {
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

    protected Pedido pedidoResponseDTOToPedido(PedidoResponseDTO pedidoResponseDTO) {
        if ( pedidoResponseDTO == null ) {
            return null;
        }

        Long id = null;
        Long clienteId = null;
        EstadoPedidoEnum estado = null;
        LocalDate dataDoPagamento = null;

        id = pedidoResponseDTO.id();
        clienteId = pedidoResponseDTO.clienteId();
        estado = pedidoResponseDTO.estado();
        dataDoPagamento = pedidoResponseDTO.dataDoPagamento();

        List<PedidoProduto> pedidosProdutos = null;

        Pedido pedido = new Pedido( id, clienteId, estado, dataDoPagamento, pedidosProdutos );

        return pedido;
    }

    protected Produto produtoRequestDTOToProduto(ProdutoRequestDTO produtoRequestDTO) {
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

    protected PedidoResponseDTO pedidoToPedidoResponseDTO(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        Long id = null;
        Long clienteId = null;
        EstadoPedidoEnum estado = null;
        LocalDate dataDoPagamento = null;

        id = pedido.getId();
        clienteId = pedido.getClienteId();
        estado = pedido.getEstado();
        dataDoPagamento = pedido.getDataDoPagamento();

        List<PedidoProdutoResponseDTO> pedidosProdutos = null;

        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO( id, clienteId, estado, pedidosProdutos, dataDoPagamento );

        return pedidoResponseDTO;
    }

    protected ProdutoRequestDTO produtoToProdutoRequestDTO(Produto produto) {
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
}
