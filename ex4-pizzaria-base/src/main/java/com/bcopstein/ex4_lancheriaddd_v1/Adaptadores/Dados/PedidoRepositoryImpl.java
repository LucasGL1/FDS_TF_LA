package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {
    private List<Pedido> pedidos;

    public PedidoRepositoryImpl() {
        this.pedidos = new ArrayList<>();
        
        Cliente clienteExemplo = new Cliente(1L, "123.456.789-00", "Cliente Frequente", "51999998888", "Rua dos Testes, 123", "cliente@teste.com");

        pedidos.add(new Pedido(1L, clienteExemplo, LocalDateTime.now().minusDays(5), new ArrayList<>(), Pedido.Status.ENTREGUE, 50, 5, 0, 55));
        pedidos.add(new Pedido(2L, clienteExemplo, LocalDateTime.now().minusDays(4), new ArrayList<>(), Pedido.Status.ENTREGUE, 60, 6, 0, 66));
        pedidos.add(new Pedido(3L, clienteExemplo, LocalDateTime.now().minusDays(3), new ArrayList<>(), Pedido.Status.ENTREGUE, 40, 4, 0, 44));
        pedidos.add(new Pedido(4L, clienteExemplo, LocalDateTime.now().minusDays(2), new ArrayList<>(), Pedido.Status.ENTREGUE, 70, 7, 0, 77));
    }

    @Override
    public List<Pedido> findAll() {
        return new ArrayList<>(pedidos);
    }

    @Override
    public Pedido save(Pedido pedido) {
        pedidos.removeIf(p -> p.getId() == pedido.getId());
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
        return pedidos.stream()
                .filter(p -> p.getCliente() != null && p.getCliente().getId() == idCliente)
                .filter(p -> p.getDataHoraPagamento() != null && p.getDataHoraPagamento().isAfter(LocalDateTime.now().minusDays(dias)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> findEntreguesByData(LocalDate dataInicio, LocalDate dataFim) {
        return pedidos.stream()
            .filter(p -> p.getStatus() == Pedido.Status.ENTREGUE)
            .filter(p -> p.getDataHoraPagamento() != null &&
                         !p.getDataHoraPagamento().toLocalDate().isBefore(dataInicio) &&
                         !p.getDataHoraPagamento().toLocalDate().isAfter(dataFim))
            .collect(Collectors.toList());
    }
}