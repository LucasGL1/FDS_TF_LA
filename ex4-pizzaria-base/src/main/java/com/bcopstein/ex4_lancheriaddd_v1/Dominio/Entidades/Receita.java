package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import java.util.List;

@Entity
@Table(name = "receitas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receita {
    @Id
    private long id;
    
    private String titulo;
    
    // O FetchType.EAGER obriga o JPA a carregar os ingredientes imediatamente
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "receita_ingrediente", // Nome da tabela no banco (ver schema.sql)
        joinColumns = @JoinColumn(name = "receita_id"), // Coluna da receita
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id") // Coluna do ingrediente
    )
    private List<Ingrediente> ingredientes;
}