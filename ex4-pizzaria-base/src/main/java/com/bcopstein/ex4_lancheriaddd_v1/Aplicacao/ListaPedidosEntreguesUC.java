package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponseDTO; // NOVO IMPORT
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors; // NOVO IMPORT

@Component
public class ListaPedidosEntreguesUC {
    private PedidoRepository pedidos;

    @Autowired
    public ListaPedidosEntreguesUC(PedidoRepository pedidos) {
        this.pedidos = pedidos;
    }

    // O método agora retorna uma lista de DTOs
    public List<PedidoResponseDTO> run(LocalDate dataInicio, LocalDate dataFim) {
        // 1. Busca as entidades de Pedido
        List<Pedido> pedidosEntidade = pedidos.findEntreguesByData(dataInicio, dataFim);
        
        // 2. Converte a lista de Entidades para uma lista de DTOs
        return pedidosEntidade.stream()
                .map(pedido -> new PedidoResponseDTO(pedido))
                .collect(Collectors.toList());
    }
}