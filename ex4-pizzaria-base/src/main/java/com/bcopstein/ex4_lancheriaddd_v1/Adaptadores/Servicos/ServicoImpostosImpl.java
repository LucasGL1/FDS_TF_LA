package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Servicos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoImpostos;
import org.springframework.stereotype.Component;

@Component
public class ServicoImpostosImpl implements ServicoImpostos {
    @Override
    public double calculaImposto(double subTotal) {
        return subTotal * 0.10;
    }
}