package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

public interface ServicoPagamento {
    boolean efetuaPagamento(long idPedido, double valor);
}