// O package deve incluir "Adaptadores"
package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Servicos; 

import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoEstoque;

@Component
public class ServicoEstoqueFakeImpl implements ServicoEstoque {
    @Override
    public boolean verificaDisponibilidade(Pedido pedido) {
        return true;
    }
}