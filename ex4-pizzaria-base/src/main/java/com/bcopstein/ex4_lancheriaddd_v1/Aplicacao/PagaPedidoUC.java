package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoCozinha;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoEntrega;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class PagaPedidoUC {
    private final PedidoRepository pedidos;
    private final ServicoPagamento servicoPagamento;
    private final ServicoCozinha servicoCozinha;
    private final ServicoEntrega servicoEntrega;

    @Autowired
    public PagaPedidoUC(PedidoRepository pedidos, ServicoPagamento servicoPagamento, ServicoCozinha servicoCozinha, ServicoEntrega servicoEntrega) {
        this.pedidos = pedidos;
        this.servicoPagamento = servicoPagamento;
        this.servicoCozinha = servicoCozinha;
        this.servicoEntrega = servicoEntrega;
    }

    public Pedido run(long idPedido) {
        Pedido pedido = pedidos.findById(idPedido);
        if (pedido == null || pedido.getStatus() != Pedido.Status.APROVADO) {
            return null; 
        }

        boolean pagamentoOk = servicoPagamento.efetuaPagamento(idPedido, pedido.getValorCobrado());
        if (pagamentoOk) {
            pedido.setStatus(Pedido.Status.PAGO);
            pedido.setDataHoraPagamento(LocalDateTime.now()); 
            pedidos.save(pedido);

            servicoCozinha.iniciaPreparo(pedido);
            pedido.setStatus(Pedido.Status.PRONTO);
            pedidos.save(pedido);

            servicoEntrega.iniciaEntrega(pedido);
            pedido.setStatus(Pedido.Status.ENTREGUE);
            pedidos.save(pedido);
            
            return pedido;
        }
        return null;
    }
}