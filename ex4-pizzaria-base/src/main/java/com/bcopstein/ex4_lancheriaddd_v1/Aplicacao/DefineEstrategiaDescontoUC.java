package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
// import java.util.List; <--- REMOVIDO

@Component
public class DefineEstrategiaDescontoUC {
    private ConfiguradorDesconto configurador;

    @Autowired
    public DefineEstrategiaDescontoUC(ConfiguradorDesconto configurador) {
        this.configurador = configurador;
    }

    public void run(String nomeEstrategia) {
        configurador.setNomeEstrategiaAtiva(nomeEstrategia);
    }
}