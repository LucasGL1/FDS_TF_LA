package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Servicos;

import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoCozinha;

@Component
public class ServicoCozinhaSimuladoImpl implements ServicoCozinha {
    @Override
    public void iniciaPreparo(Pedido pedido) {
        System.out.println("LOG: Pedido " + pedido.getId() + " -> AGUARDANDO");
        System.out.println("LOG: Pedido " + pedido.getId() + " -> PREPARACAO");
        System.out.println("LOG: Pedido " + pedido.getId() + " -> PRONTO");
    }
}