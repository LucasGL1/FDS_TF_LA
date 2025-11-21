package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "ingredientes") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Ingrediente {
    
    @Id 
    private long id;
    
    private String descricao;
}