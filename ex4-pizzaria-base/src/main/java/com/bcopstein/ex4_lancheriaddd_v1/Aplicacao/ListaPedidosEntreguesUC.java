package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class ListaPedidosEntreguesUC {
    private PedidoRepository pedidos;

    @Autowired
    public ListaPedidosEntreguesUC(PedidoRepository pedidos) {
        this.pedidos = pedidos;
    }

    public List<Pedido> run(LocalDate dataInicio, LocalDate dataFim) {
        return pedidos.findEntreguesByData(dataInicio, dataFim);
    }
}