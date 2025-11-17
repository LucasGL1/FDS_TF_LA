package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoResponseDTO {
    private long id;
    private Pedido.Status status;
    private double valorTotal;
    private double desconto;
    private List<ItemPedidoResponseDTO> itens;

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.status = pedido.getStatus();
        
        // --- CORREÇÃO AQUI ---
        // Arredonda o valor para 2 casas decimais
        this.valorTotal = Math.round(pedido.getValorCobrado() * 100.0) / 100.0;
        
        this.desconto = pedido.getDesconto();
        this.itens = pedido.getItens().stream()
            .map(item -> new ItemPedidoResponseDTO(
                item.getItem().getDescricao(),
                item.getItem().getPreco() / 100.0,
                item.getQuantidade()
            ))
            .collect(Collectors.toList());
    }

    // Getters
    public long getId() { return id; }
    public Pedido.Status getStatus() { return status; }
    public double getValorTotal() { return valorTotal; }
    public double getDesconto() { return desconto; }
    public List<ItemPedidoResponseDTO> getItens() { return itens; }
}