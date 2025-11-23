package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
public class Produto {
    @Id
    private long id;
    
    private String descricao;
    
    @ManyToOne
    private Receita receita;
    
    private int preco;

    public Produto(long id, String descricao, Receita receita, int preco) {
        this.id = id;
        this.descricao = descricao;
        this.receita = receita;
        this.preco = preco;
    }

    public void setPreco(int preco) {
        if (preco <= 0) throw new IllegalArgumentException("Preco invalido: " + preco);
        this.preco = preco;
    }
}