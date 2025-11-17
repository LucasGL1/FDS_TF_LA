package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;

/**
 * Esta classe (marcada como @Component) é um "Singleton" que guarda
 * o estado de qual cardápio está ativo na memória do servidor.
 * Ela começa com o padrão '1L', como o enunciado pedia.
 */
@Component
public class ConfiguradorCardapio {
    private long idCardapioAtivo = 1L;

    public long getIdCardapioAtivo() {
        return idCardapioAtivo;
    }

    public void setIdCardapioAtivo(long idCardapioAtivo) {
        this.idCardapioAtivo = idCardapioAtivo;
    }
}