package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsultaStatusUC {
    private PedidoRepository pedidos;

    @Autowired
    public ConsultaStatusUC(PedidoRepository pedidos) {
        this.pedidos = pedidos;
    }

    public StatusPedido run(long idPedido) {
        Pedido pedido = pedidos.findById(idPedido);
        return (pedido != null) ? pedido.getStatus() : null;
    }
}