package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import java.util.List;

public interface PedidoRepository {
    List<Pedido> findAll();
    Pedido save(Pedido pedido);
    Pedido findById(long id);
    List<Pedido> findPedidosRecentesByCliente(long idCliente, int dias);
}