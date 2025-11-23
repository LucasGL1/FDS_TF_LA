package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Servicos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServicoImpostosImplTest {

    private ServicoImpostosImpl servicoImpostos;

    @BeforeEach
    public void setup() {
        // Instancia a classe que queremos testar
        servicoImpostos = new ServicoImpostosImpl();
    }

    @ParameterizedTest
    @CsvSource({
        "100.0, 10.0",
        "0.0, 0.0",
        "55.50, 5.55"
    })
    @DisplayName("Teste do Cálculo de Imposto 10%")
    public void testeCalculoImposto(double subTotal, double impostoEsperado) {
        double impostoCalculado = servicoImpostos.calculaImposto(subTotal);
        assertEquals(impostoEsperado, impostoCalculado, 0.001); 
    }
}