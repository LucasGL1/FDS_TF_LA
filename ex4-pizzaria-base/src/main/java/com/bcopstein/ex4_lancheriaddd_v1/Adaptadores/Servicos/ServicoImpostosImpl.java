package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Servicos;

import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoImpostos;

@Component
public class ServicoImpostosImpl implements ServicoImpostos {
    @Override
    public double calculaImposto(double subTotal) {
        return subTotal * 0.10;
    }
}