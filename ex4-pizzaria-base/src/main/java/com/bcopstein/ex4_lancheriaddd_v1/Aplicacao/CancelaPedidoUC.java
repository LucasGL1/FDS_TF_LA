package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponseDTO;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
// NOVOS IMPORTS
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.OperacaoInvalidaException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.PedidoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelaPedidoUC {
    private PedidoRepository pedidos;

    @Autowired
    public CancelaPedidoUC(PedidoRepository pedidos) {
        this.pedidos = pedidos;
    }

    public PedidoResponseDTO run(long idPedido) {
        Pedido pedido = pedidos.findById(idPedido);

        if (pedido == null) {
            throw new PedidoNaoEncontradoException("Pedido " + idPedido + " não encontrado.");
        }
        // Validação da regra de negócio
        if (pedido.getStatus() != Pedido.Status.APROVADO) {
            throw new OperacaoInvalidaException("Não é possível cancelar um pedido que não está com o status APROVADO.");
        }
        
        pedido.setStatus(Pedido.Status.CANCELADO);
        Pedido pedidoSalvo = pedidos.save(pedido);
        return new PedidoResponseDTO(pedidoSalvo);
    }
}