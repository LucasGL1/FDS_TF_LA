//OK

package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class ConsultaStatusUC {
    private PedidoRepository pedidos;

    @Autowired
    public ConsultaStatusUC(PedidoRepository pedidos) {
        this.pedidos = pedidos;
    }

    public Pedido.Status run(long idPedido) {
        Pedido pedido = pedidos.findById(idPedido);
        if (pedido != null) {
            return pedido.getStatus();
        } else {
            return null;
        }
    }
}