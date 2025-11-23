package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "itens_estoque") // <--- MUDOU PARA SNAKE_CASE
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class ItemEstoque {
    @Id 
    private long id;
    private int quantidade;
    
    @OneToOne
    private Ingrediente ingrediente;
}