package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;

// Este DTO contém APENAS os campos que o cliente pode ver.
// Note que ele não tem o campo "receita".
public class ProdutoCardapioDTO {
    private long id;
    private String descricao;
    private double preco; // Preço em Reais (ex: 55.00)

    public ProdutoCardapioDTO(long id, String descricao, int precoEmCentavos) {
        this.id = id;
        this.descricao = descricao;
        // Converte o preço de centavos (int) para reais (double)
        this.preco = precoEmCentavos / 100.0; 
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }
}