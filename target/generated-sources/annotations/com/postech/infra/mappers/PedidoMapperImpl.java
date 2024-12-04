package com.postech.infra.mappers;

import com.postech.domain.entities.Pedido;
import com.postech.domain.entities.PedidoProduto;
import com.postech.domain.enums.EstadoPedidoEnum;
import com.postech.infra.dto.request.PedidoProdutoRequestDTO;
import com.postech.infra.dto.request.PedidoRequestDTO;
import com.postech.infra.dto.response.PedidoProdutoResponseDTO;
import com.postech.infra.dto.response.PedidoResponseDTO;
import com.postech.infra.persistence.entities.PedidoEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T21:10:17-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Azul Systems, Inc.)"
)
@Component
public class PedidoMapperImpl implements PedidoMapper {

    @Autowired
    private PedidoProdutoMapper pedidoProdutoMapper;

    @Override
    public PedidoEntity paraEntidade(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        PedidoEntity pedidoEntity = new PedidoEntity();

        pedidoEntity.setId( pedido.getId() );
        pedidoEntity.setClienteId( pedido.getClienteId() );
        pedidoEntity.setEstado( pedido.getEstado() );
        pedidoEntity.setDataDoPagamento( pedido.getDataDoPagamento() );
        pedidoEntity.setPedidosProdutos( pedidoProdutoMapper.paraEntidadeLista( pedido.getPedidosProdutos() ) );

        return pedidoEntity;
    }

    @Override
    public Pedido paraDominio(PedidoEntity pedidoEntity) {
        if ( pedidoEntity == null ) {
            return null;
        }

        Long id = null;
        Long clienteId = null;
        EstadoPedidoEnum estado = null;
        List<PedidoProduto> pedidosProdutos = null;
        LocalDate dataDoPagamento = null;

        id = pedidoEntity.getId();
        clienteId = pedidoEntity.getClienteId();
        estado = pedidoEntity.getEstado();
        pedidosProdutos = pedidoProdutoMapper.paraDominioListaEntidade( pedidoEntity.getPedidosProdutos() );
        dataDoPagamento = pedidoEntity.getDataDoPagamento();

        Pedido pedido = new Pedido( id, clienteId, estado, dataDoPagamento, pedidosProdutos );

        return pedido;
    }

    @Override
    public PedidoResponseDTO paraResponseDto(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        Long id = null;
        Long clienteId = null;
        EstadoPedidoEnum estado = null;
        List<PedidoProdutoResponseDTO> pedidosProdutos = null;
        LocalDate dataDoPagamento = null;

        id = pedido.getId();
        clienteId = pedido.getClienteId();
        estado = pedido.getEstado();
        pedidosProdutos = pedidoProdutoMapper.paraDTOLista( pedido.getPedidosProdutos() );
        dataDoPagamento = pedido.getDataDoPagamento();

        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO( id, clienteId, estado, pedidosProdutos, dataDoPagamento );

        return pedidoResponseDTO;
    }

    @Override
    public Pedido paraDominio(PedidoResponseDTO pedidoResponseDTO) {
        if ( pedidoResponseDTO == null ) {
            return null;
        }

        Long id = null;
        Long clienteId = null;
        EstadoPedidoEnum estado = null;
        List<PedidoProduto> pedidosProdutos = null;
        LocalDate dataDoPagamento = null;

        id = pedidoResponseDTO.id();
        clienteId = pedidoResponseDTO.clienteId();
        estado = pedidoResponseDTO.estado();
        pedidosProdutos = pedidoProdutoMapper.paraDominioListaDTO( pedidoResponseDTO.pedidosProdutos() );
        dataDoPagamento = pedidoResponseDTO.dataDoPagamento();

        Pedido pedido = new Pedido( id, clienteId, estado, dataDoPagamento, pedidosProdutos );

        return pedido;
    }

    @Override
    public Pedido paraDominio(PedidoRequestDTO pedidoRequestDTO) {
        if ( pedidoRequestDTO == null ) {
            return null;
        }

        Long clienteId = null;
        List<PedidoProduto> pedidosProdutos = null;

        clienteId = pedidoRequestDTO.getClienteId();
        pedidosProdutos = pedidoProdutoRequestDTOListToPedidoProdutoList( pedidoRequestDTO.getPedidosProdutos() );

        Long id = null;
        EstadoPedidoEnum estado = null;
        LocalDate dataDoPagamento = null;

        Pedido pedido = new Pedido( id, clienteId, estado, dataDoPagamento, pedidosProdutos );

        return pedido;
    }

    @Override
    public List<PedidoEntity> paraEntidadeLista(List<Pedido> pedidos) {
        if ( pedidos == null ) {
            return null;
        }

        List<PedidoEntity> list = new ArrayList<PedidoEntity>( pedidos.size() );
        for ( Pedido pedido : pedidos ) {
            list.add( paraEntidade( pedido ) );
        }

        return list;
    }

    @Override
    public List<Pedido> paraDominioListaEntidade(List<PedidoEntity> pedidosEntidade) {
        if ( pedidosEntidade == null ) {
            return null;
        }

        List<Pedido> list = new ArrayList<Pedido>( pedidosEntidade.size() );
        for ( PedidoEntity pedidoEntity : pedidosEntidade ) {
            list.add( paraDominio( pedidoEntity ) );
        }

        return list;
    }

    @Override
    public List<Pedido> paraDominioListaDTO(List<PedidoResponseDTO> pedidosDTO) {
        if ( pedidosDTO == null ) {
            return null;
        }

        List<Pedido> list = new ArrayList<Pedido>( pedidosDTO.size() );
        for ( PedidoResponseDTO pedidoResponseDTO : pedidosDTO ) {
            list.add( paraDominio( pedidoResponseDTO ) );
        }

        return list;
    }

    @Override
    public List<PedidoResponseDTO> paraDTOLista(List<Pedido> pedidos) {
        if ( pedidos == null ) {
            return null;
        }

        List<PedidoResponseDTO> list = new ArrayList<PedidoResponseDTO>( pedidos.size() );
        for ( Pedido pedido : pedidos ) {
            list.add( paraResponseDto( pedido ) );
        }

        return list;
    }

    protected PedidoProduto pedidoProdutoRequestDTOToPedidoProduto(PedidoProdutoRequestDTO pedidoProdutoRequestDTO) {
        if ( pedidoProdutoRequestDTO == null ) {
            return null;
        }

        PedidoProduto pedidoProduto = new PedidoProduto();

        pedidoProduto.setQuantidade( pedidoProdutoRequestDTO.getQuantidade() );

        return pedidoProduto;
    }

    protected List<PedidoProduto> pedidoProdutoRequestDTOListToPedidoProdutoList(List<PedidoProdutoRequestDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<PedidoProduto> list1 = new ArrayList<PedidoProduto>( list.size() );
        for ( PedidoProdutoRequestDTO pedidoProdutoRequestDTO : list ) {
            list1.add( pedidoProdutoRequestDTOToPedidoProduto( pedidoProdutoRequestDTO ) );
        }

        return list1;
    }
}
