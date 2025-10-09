package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime; // Import necessário para trabalhar com datas
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {
    private List<Pedido> pedidos;

    public PedidoRepositoryImpl() {
        this.pedidos = new ArrayList<>();
        
        // --- DADOS DE EXEMPLO PARA TESTAR A REGRA DE DESCONTO ---
        // Cliente de exemplo que terá um histórico de pedidos
        Cliente clienteExemplo = new Cliente(1L, "123.456.789-00", "Cliente Frequente", "51999998888", "Rua dos Testes, 123", "cliente@teste.com");

        // 4 pedidos antigos para este cliente, feitos nos últimos 5 dias
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
}