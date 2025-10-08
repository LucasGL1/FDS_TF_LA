package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados; 

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {
    private List<Pedido> pedidos;

    public PedidoRepositoryImpl() {
        this.pedidos = new ArrayList<>();
    }

    @Override
    public Pedido save(Pedido pedido) {
        pedidos.add(pedido);
        return pedido;
    }

    @Override
    public Pedido findById(long id) {
        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Pedido> findPedidosRecentesByCliente(long idCliente, int dias) {
        return new ArrayList<>();
    }
}