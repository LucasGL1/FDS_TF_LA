package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClienteRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Repository // Esta anotação registra o "bean"
public class PedidoRepositoryJDBC implements PedidoRepository {

    private JdbcTemplate jdbcTemplate;
    private ClienteRepository clienteRepository;
    private ProdutosRepository produtosRepository; 

    @Autowired
    public PedidoRepositoryJDBC(JdbcTemplate jdbcTemplate, ClienteRepository clienteRepository, ProdutosRepository produtosRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.clienteRepository = clienteRepository;
        this.produtosRepository = produtosRepository;
    }

    private Pedido mapRowToPedido(ResultSet rs, int rowNum) throws SQLException {
        long clienteId = rs.getLong("cliente_id");
        Cliente cliente = clienteRepository.findById(clienteId);
        
        Pedido pedido = new Pedido();
        pedido.setId(rs.getLong("id"));
        pedido.setCliente(cliente);
        pedido.setDataHoraPagamento(rs.getTimestamp("data_hora_pagamento") != null ? rs.getTimestamp("data_hora_pagamento").toLocalDateTime() : null);
        pedido.setStatus(Pedido.Status.valueOf(rs.getString("status")));
        pedido.setValor(rs.getDouble("valor"));
        pedido.setImpostos(rs.getDouble("impostos"));
        pedido.setDesconto(rs.getDouble("desconto"));
        pedido.setValorCobrado(rs.getDouble("valor_cobrado"));
        
        // Carrega os itens do pedido
        pedido.setItens(findItensByPedidoId(pedido.getId()));
        return pedido;
    }

    private List<ItemPedido> findItensByPedidoId(long pedidoId) {
        String sql = "SELECT * FROM item_pedido WHERE pedido_id = ?";
        return jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, pedidoId),
            (rs, rowNum) -> {
                ItemPedido item = new ItemPedido();
                item.setItem(produtosRepository.recuperaProdutoPorid(rs.getLong("produto_id")));
                item.setQuantidade(rs.getInt("quantidade"));
                return item;
            }
        );
    }

    @Override
    public List<Pedido> findAll() {
        String sql = "SELECT * FROM pedidos";
        return jdbcTemplate.query(sql, this::mapRowToPedido);
    }

    @Override
    public Pedido findById(long id) {
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        List<Pedido> pedidos = jdbcTemplate.query(sql, ps -> ps.setLong(1, id), this::mapRowToPedido);
        return pedidos.isEmpty() ? null : pedidos.get(0);
    }

    @Override
    public List<Pedido> findPedidosRecentesByCliente(long idCliente, int dias) {
        String sql = "SELECT * FROM pedidos WHERE cliente_id = ? AND data_hora_pagamento >= ?";
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(dias);
        return jdbcTemplate.query(sql, ps -> {
            ps.setLong(1, idCliente);
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(dataLimite));
        }, this::mapRowToPedido);
    }

    @Override
    public List<Pedido> findEntreguesByData(LocalDate dataInicio, LocalDate dataFim) {
        String sql = "SELECT * FROM pedidos WHERE status = 'ENTREGUE' AND data_hora_pagamento BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, ps -> {
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(dataInicio.atStartOfDay()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(dataFim.plusDays(1).atStartOfDay()));
        }, this::mapRowToPedido);
    }

    @Override
    public Pedido save(Pedido pedido) {
        Pedido pedidoExistente = findById(pedido.getId());
        
        if (pedidoExistente == null) {
            String sqlPedido = "INSERT INTO pedidos (id, cliente_id, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlPedido,
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getDataHoraPagamento(),
                pedido.getStatus().name(),
                pedido.getValor(),
                pedido.getImpostos(),
                pedido.getDesconto(),
                pedido.getValorCobrado()
            );
            
            String sqlItem = "INSERT INTO item_pedido (pedido_id, produto_id, quantidade) VALUES (?, ?, ?)";
            for (ItemPedido item : pedido.getItens()) {
                jdbcTemplate.update(sqlItem,
                    pedido.getId(),
                    item.getItem().getId(),
                    item.getQuantidade()
                );
            }
        } else {
            String sql = "UPDATE pedidos SET status = ?, valor = ?, impostos = ?, desconto = ?, valor_cobrado = ?, data_hora_pagamento = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                pedido.getStatus().name(),
                pedido.getValor(),
                pedido.getImpostos(),
                pedido.getDesconto(),
                pedido.getValorCobrado(),
                pedido.getDataHoraPagamento(),
                pedido.getId()
            );
        }
        return pedido;
    }
}