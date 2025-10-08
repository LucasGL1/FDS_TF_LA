package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.util.Collections; // Linha corrigida
import java.util.List;

import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

@Component
public class CarregaCardapioUC {
    public List<Produto> run() { 
        //implementar
        System.out.println("WARN: CarregaCardapioUC não implementado!");
        return Collections.emptyList();
    }
}