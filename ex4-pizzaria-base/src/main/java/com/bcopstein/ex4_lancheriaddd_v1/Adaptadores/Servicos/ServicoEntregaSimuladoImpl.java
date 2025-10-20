package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Servicos;

import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoEntrega;

@Component
public class ServicoEntregaSimuladoImpl implements ServicoEntrega {
    @Override
    public void iniciaEntrega(Pedido pedido) {
        System.out.println("LOG: Pedido " + pedido.getId() + " -> TRANSPORTE");
        System.out.println("LOG: Pedido " + pedido.getId() + " -> ENTREGUE");
    }
}