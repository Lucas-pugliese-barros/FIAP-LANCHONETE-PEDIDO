package com.postech.application.usecases;

import com.postech.application.client.PaymentClient;
import com.postech.application.gateways.RepositorioDePedidoGateway;
import com.postech.domain.entities.PedidoProduto;
import com.postech.domain.exceptions.DominioException;
import com.postech.domain.exceptions.PedidoException;
import com.postech.domain.entities.Pedido;
import com.postech.domain.enums.ErroPedidoEnum;
import com.postech.domain.enums.EstadoPedidoEnum;
import com.postech.infra.dto.request.PedidoProdutoRequestDTO;
import com.postech.infra.dto.request.PedidoRequestDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.postech.application.utils.EstadoPedidoUtils.validaEstado;

public class PedidoUseCases {

    private static final Logger LOG = Logger.getLogger(PedidoUseCases.class.getName());

    private final RepositorioDePedidoGateway repositorioDePedido;

    private final ProdutoUseCases produtoUseCases;

    private final PaymentClient paymentClient;

    public PedidoUseCases(RepositorioDePedidoGateway repositorioDePedido, ProdutoUseCases produtoUseCases,
                          PaymentClient paymentClient) {
        this.repositorioDePedido = repositorioDePedido;
        this.produtoUseCases = produtoUseCases;
        this.paymentClient = paymentClient;
    }

    public Pedido consultaPorId(Long id) {
        Pedido pedido = repositorioDePedido.consultaPedidoPorId(id);

        if (pedido == null) {
            throw new PedidoException(ErroPedidoEnum.PEDIDO_NAO_ENCONTRADO);
        }

        return pedido;
    }

    public Pedido atualizaEstadoPorIdDoPedido(Long idDoPedido, EstadoPedidoEnum estado) {
        Pedido pedido = this.consultaPorId(idDoPedido);

        if (!validaEstado(pedido.getEstado(), estado)) {
            throw new PedidoException(ErroPedidoEnum.ESTADO_INVALIDO);
        }

        if (estado.equals(EstadoPedidoEnum.PAGO)) {
            pedido.setDataDoPagamento(LocalDate.now());
        }

        pedido.setEstado(estado);

        return repositorioDePedido.salvaPedido(pedido);

    }

    public Pedido notificaEstado(Long id) {
        return this.consultaPorId(id);
    }

    public List<Pedido> consultaTodosOsPedidos() {
        List<Pedido> pedidos = repositorioDePedido.buscaTodosPedidos();

        if (pedidos.isEmpty()) {
            throw new PedidoException(ErroPedidoEnum.PEDIDOS_NAO_ECONTRADOS);
        }

        return pedidos;
    }

    public Pedido checkout(Long id) {
        return this.atualizaEstadoPorIdDoPedido(id, EstadoPedidoEnum.PREPARANDO);
    }

    public void deleta(Long id) {
        try {
            repositorioDePedido.deletaPedido(id);
        } catch (Exception ignore) {
            throw new PedidoException(ErroPedidoEnum.PEDIDO_NAO_ENCONTRADO);
        }
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = consultaTodosOsPedidos();

        List<Pedido> pedidosFiltrados = filtrarPedidos(pedidos, List.of(EstadoPedidoEnum.CANCELADO, EstadoPedidoEnum.FINALIZADO));

        return ordenarListarPedidos(pedidosFiltrados);
    }

    protected List<Pedido> ordenarListarPedidos(List<Pedido> pedidos) {
        return pedidos.stream()
                .sorted(Comparator.comparing((Pedido p) -> p.getEstado().getOrdem(), Comparator.reverseOrder())
                        .thenComparing(Pedido::getId))
                .toList();
    }

    protected List<Pedido> filtrarPedidos(List<Pedido> pedidos, List<EstadoPedidoEnum> estadosParaRetirar){
        return pedidos.stream().filter(x -> !estadosParaRetirar.contains(x.getEstado())).collect(Collectors.toList());
    }

    public Pedido salvarPedido(Pedido pedido){
        List<PedidoProduto> pedidosProdutos = pedido.getPedidosProdutos();

        pedido.setPedidosProdutos(null);

        Pedido pedidoSalvo = repositorioDePedido.salvaPedido(pedido);

        pedidosProdutos.forEach(x -> {
            x.setPedido(pedidoSalvo);
        });

        pedidoSalvo.setPedidosProdutos(pedidosProdutos);

        Pedido pedidoFinal = repositorioDePedido.salvaPedido(pedidoSalvo);

        try {
            paymentClient.enviarPagamento(pedidoFinal.getClienteId(), pedidoFinal.getId(), Pedido.getValorPedido(pedidoFinal));
        } catch (Exception exception) {
            LOG.log(Level.SEVERE, "Error ao tentar enviar pagamento");
            throw new DominioException("Error ao tentar enviar pagamento", exception);
        }

        return pedidoFinal;
    }

    public Pedido criaPedido(PedidoRequestDTO pedidoDTO) {
        List<PedidoProdutoRequestDTO> pedidosProdutos = pedidoDTO.getPedidosProdutos();

        List<PedidoProduto> pedidoProdutos = new ArrayList<>();

        for (PedidoProdutoRequestDTO pedidosProduto : pedidosProdutos) {
            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setProduto(produtoUseCases.consultaPorId(pedidosProduto.getProdutoId()));
            pedidoProduto.setQuantidade(pedidosProduto.getQuantidade());
            pedidoProdutos.add(pedidoProduto);
        }

        return new Pedido(null, pedidoDTO.getClienteId(), EstadoPedidoEnum.RECEBIDO, null, pedidoProdutos);
    }
}
