package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;

@Component
public class ConfiguradorDesconto {
    private String nomeEstrategiaAtiva = "ClienteFrequente";

    public String getNomeEstrategiaAtiva() {
        return nomeEstrategiaAtiva;
    }

    public void setNomeEstrategiaAtiva(String nomeEstrategiaAtiva) {
        this.nomeEstrategiaAtiva = nomeEstrategiaAtiva;
    }
}