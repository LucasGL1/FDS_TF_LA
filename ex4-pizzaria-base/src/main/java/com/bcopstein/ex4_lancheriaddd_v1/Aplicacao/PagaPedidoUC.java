package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponseDTO;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
// NOVOS IMPORTS
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.OperacaoInvalidaException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.PedidoNaoEncontradoException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoCozinha;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoEntrega;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class PagaPedidoUC {
    // ... (dependências)
    private final PedidoRepository pedidos;
    private final ServicoPagamento servicoPagamento;
    private final ServicoCozinha servicoCozinha;
    private final ServicoEntrega servicoEntrega;

    @Autowired
    public PagaPedidoUC(PedidoRepository pedidos, ServicoPagamento servicoPagamento, ServicoCozinha servicoCozinha, ServicoEntrega servicoEntrega) {
        // ... (atribuição)
        this.pedidos = pedidos;
        this.servicoPagamento = servicoPagamento;
        this.servicoCozinha = servicoCozinha;
        this.servicoEntrega = servicoEntrega;
    }

    public PedidoResponseDTO run(long idPedido) {
        Pedido pedido = pedidos.findById(idPedido);
        
        if (pedido == null) {
            throw new PedidoNaoEncontradoException("Pedido " + idPedido + " não encontrado.");
        }
        if (pedido.getStatus() != Pedido.Status.APROVADO) {
            throw new OperacaoInvalidaException("Este pedido não pode ser pago (Status atual: " + pedido.getStatus() + ")");
        }

        boolean pagamentoOk = servicoPagamento.efetuaPagamento(idPedido, pedido.getValorCobrado());
        
        if (!pagamentoOk) {
             throw new OperacaoInvalidaException("Pagamento rejeitado.");
        }

        // Fluxo de pagamento
        pedido.setStatus(Pedido.Status.PAGO);
        pedido.setDataHoraPagamento(LocalDateTime.now()); 
        pedidos.save(pedido);

        servicoCozinha.iniciaPreparo(pedido);
        pedido.setStatus(Pedido.Status.PRONTO);
        pedidos.save(pedido);

        servicoEntrega.iniciaEntrega(pedido);
        pedido.setStatus(Pedido.Status.ENTREGUE);
        Pedido pedidoSalvo = pedidos.save(pedido);
            
        return new PedidoResponseDTO(pedidoSalvo);
    }
}