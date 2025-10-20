package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import java.time.LocalDate;
import java.util.List; 

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public interface PedidoRepository {
    List<Pedido> findAll();
    Pedido save(Pedido pedido);
    Pedido findById(long id);
    List<Pedido> findPedidosRecentesByCliente(long idCliente, int dias);
    List<Pedido> findEntreguesByData(LocalDate dataInicio, LocalDate dataFim);
}