package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class ListaPedidosUC {
    private PedidoRepository pedidos;

    @Autowired
    public ListaPedidosUC(PedidoRepository pedidos) {
        this.pedidos = pedidos;
    }

    public List<Pedido> run() {
        return pedidos.findAll();
    }
}