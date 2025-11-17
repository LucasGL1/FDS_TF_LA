package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;

// DTO para representar um item DENTRO de uma resposta de pedido
public class ItemPedidoResponseDTO {
    private String descricao;
    private double precoUnitario;
    private int quantidade;

    public ItemPedidoResponseDTO(String descricao, double precoUnitario, int quantidade) {
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    // Getters
    public String getDescricao() { return descricao; }
    public double getPrecoUnitario() { return precoUnitario; }
    public int getQuantidade() { return quantidade; }
}