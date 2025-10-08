package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
// O import para StatusPedido foi removido
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelaPedidoUC {
    private PedidoRepository pedidos;

    @Autowired
    public CancelaPedidoUC(PedidoRepository pedidos) {
        this.pedidos = pedidos;
    }

    public Pedido run(long idPedido) {
        Pedido pedido = pedidos.findById(idPedido);
        if (pedido != null && pedido.getStatus() == Pedido.Status.APROVADO) {
            pedido.setStatus(Pedido.Status.CANCELADO);
            pedidos.save(pedido);
            return pedido;
        }
        return null;
    }
}