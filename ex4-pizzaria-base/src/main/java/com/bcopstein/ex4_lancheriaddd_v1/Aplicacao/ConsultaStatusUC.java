package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.PedidoNaoEncontradoException; // NOVO IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsultaStatusUC {
    private PedidoRepository pedidos;

    @Autowired
    public ConsultaStatusUC(PedidoRepository pedidos) {
        this.pedidos = pedidos;
    }

    public Pedido.Status run(long idPedido) {
        Pedido pedido = pedidos.findById(idPedido);
        // Lógica atualizada: lança exceção se for nulo
        if (pedido == null) {
            throw new PedidoNaoEncontradoException("Pedido " + idPedido + " não encontrado.");
        }
        return pedido.getStatus();
    }
}