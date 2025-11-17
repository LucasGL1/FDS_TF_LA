package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefineCardapioAtivoUC {
    private ConfiguradorCardapio configurador;

    @Autowired
    public DefineCardapioAtivoUC(ConfiguradorCardapio configurador) {
        this.configurador = configurador;
    }

    /**
     * Define qual o ID do cardápio que deve ser retornado
     * para os clientes (UC1).
     */
    public void run(long idCardapio) {
        // Em um projeto real, verificaríamos se um cardápio com este ID existe.
        // Por agora, apenas atualizamos o valor no configurador.
        configurador.setIdCardapioAtivo(idCardapio);
    }
}