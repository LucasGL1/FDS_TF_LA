package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

public class Produto {
    private long id;
    private String descricao;
    private Receita receita;
    private int preco; 

    public Produto() {} // Construtor vazio

    public Produto(long id,String descricao, Receita receita, int preco) {
        if (!Produto.precoValido(preco))
            throw new IllegalArgumentException("Preco invalido: " + preco);
        if (descricao == null || descricao.length() == 0)
            throw new IllegalArgumentException("Descricao invalida");
        if (receita == null)
            throw new IllegalArgumentException("Receita invalida");
        this.id = id;
        this.descricao = descricao;
        this.receita = receita;
        this.preco = preco;
    }

    // Getters
    public long getId(){ return id; }
    public String getDescricao() { return descricao; }
    public Receita getReceita() { return receita; }
    public int getPreco() { return preco; }

    // SETTER ADICIONADO
    public void setId(long id) {
        this.id = id;
    }

    public void setPreco(int preco) {
        if (!Produto.precoValido(preco))
            throw new IllegalArgumentException("Preco invalido: " + preco);
        this.preco = preco;
    }

    public static boolean precoValido(int preco) {
        return preco > 0;
    }
}