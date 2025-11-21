package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Servicos;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ConfiguradorDesconto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.Descontos.EstrategiaClienteFrequente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.Descontos.EstrategiaClienteGastador;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.Descontos.IEstrategiaDesconto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ServicoDescontosImplTest {

    @Mock
    private ConfiguradorDesconto configurador;
    @Mock
    private EstrategiaClienteFrequente estrategiaFrequente;
    @Mock
    private EstrategiaClienteGastador estrategiaGastador;
    @Mock
    private Pedido pedido;

    private ServicoDescontosImpl servicoDescontos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(estrategiaFrequente.getNomeEstrategia()).thenReturn("ClienteFrequente");
        when(estrategiaGastador.getNomeEstrategia()).thenReturn("ClienteGastador");
        
        List<IEstrategiaDesconto> estrategias = List.of(estrategiaFrequente, estrategiaGastador);
        servicoDescontos = new ServicoDescontosImpl(estrategias, configurador);
    }

    /*
     * ==== Especificação dos Casos de Teste ====
     * * Item 5.c: A classe que implementa o "Serviço de Descontos"
     * Regra de Negócio: Deve selecionar a estratégia ativa definida no Configurador.
     * * Caso de Teste 1: Deve selecionar "ClienteFrequente"
     * - Contexto: Configurador está setado para "ClienteFrequente".
     * - Ação: Solicitar desconto.
     * - Saída Esperada: O valor da EstrategiaClienteFrequente (ex: 0.07).
     * * Caso de Teste 2: Deve selecionar "ClienteGastador"
     * - Contexto: Configurador está setado para "ClienteGastador".
     * - Ação: Solicitar desconto.
     * - Saída Esperada: O valor da EstrategiaClienteGastador (ex: 0.15).
     * * Caso de Teste 3: Deve retornar 0 se a estratégia não existir
     * - Contexto: Configurador está setado para "EstrategiaInvalida".
     * - Ação: Solicitar desconto.
     * - Saída Esperada: 0.0
     */

    @Test
    @DisplayName("Deve usar a estratégia ClienteFrequente quando ativa")
    public void testeSelecionaClienteFrequente() {
        when(configurador.getNomeEstrategiaAtiva()).thenReturn("ClienteFrequente");
        when(estrategiaFrequente.getPercentualDesconto(pedido)).thenReturn(0.07);

        double desconto = servicoDescontos.getPercentualDesconto(pedido);

        assertEquals(0.07, desconto);
    }

    @Test
    @DisplayName("Deve usar a estratégia ClienteGastador quando ativa")
    public void testeSelecionaClienteGastador() {
        when(configurador.getNomeEstrategiaAtiva()).thenReturn("ClienteGastador");
        when(estrategiaGastador.getPercentualDesconto(pedido)).thenReturn(0.15);

        double desconto = servicoDescontos.getPercentualDesconto(pedido);

        assertEquals(0.15, desconto);
    }

    @Test
    @DisplayName("Deve retornar 0 se a estratégia ativa não for encontrada")
    public void testeEstrategiaInvalida() {
        when(configurador.getNomeEstrategiaAtiva()).thenReturn("EstrategiaInvalida");

        double desconto = servicoDescontos.getPercentualDesconto(pedido);

        assertEquals(0.0, desconto);
    }
}