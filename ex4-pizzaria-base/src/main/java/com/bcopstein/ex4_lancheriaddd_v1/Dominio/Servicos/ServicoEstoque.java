package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public interface ServicoEstoque {
    boolean verificaDisponibilidade(Pedido pedido);
}