package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Servicos;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ConfiguradorDesconto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoDescontos;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.Descontos.IEstrategiaDesconto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component 
public class ServicoDescontosImpl implements ServicoDescontos {

    private List<IEstrategiaDesconto> estrategias;
    private ConfiguradorDesconto configurador;

    @Autowired
    public ServicoDescontosImpl(List<IEstrategiaDesconto> estrategias, ConfiguradorDesconto configurador) {
        this.estrategias = estrategias;
        this.configurador = configurador;
    }


    @Override
    public double getPercentualDesconto(Pedido pedido) {
        String nomeEstrategiaAtiva = configurador.getNomeEstrategiaAtiva();

        for (IEstrategiaDesconto estrategia : estrategias) {
            if (estrategia.getNomeEstrategia().equalsIgnoreCase(nomeEstrategiaAtiva)) {
                return estrategia.getPercentualDesconto(pedido);
            }
        }
        
        return 0.0;
    }
}