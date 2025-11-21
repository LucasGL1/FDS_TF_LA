package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.Descontos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public interface IEstrategiaDesconto {

    String getNomeEstrategia();
    
    double getPercentualDesconto(Pedido pedido);
}