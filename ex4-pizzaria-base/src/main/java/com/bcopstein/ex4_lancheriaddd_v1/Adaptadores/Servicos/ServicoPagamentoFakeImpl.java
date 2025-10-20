package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Servicos;

import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoPagamento;

@Component
public class ServicoPagamentoFakeImpl implements ServicoPagamento {
    @Override
    public boolean efetuaPagamento(long idPedido, double valor) {
        System.out.println("LOG: Pagamento de R$" + valor + " para o pedido " + idPedido + " APROVADO.");
        return true;
    }
}