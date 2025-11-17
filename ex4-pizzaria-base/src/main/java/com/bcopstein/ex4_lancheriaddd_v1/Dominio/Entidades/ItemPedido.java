package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

public class ItemPedido {
    private Produto item;
    private int quantidade;

    public ItemPedido() {} // Construtor vazio

    public ItemPedido(Produto item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    // Getters
    public Produto getItem() { return item; }
    public int getQuantidade() { return quantidade; }

    // SETTERS ADICIONADOS
    public void setItem(Produto item) {
        this.item = item;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}