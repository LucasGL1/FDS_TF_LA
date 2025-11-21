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
        servicoImpostos = new ServicoImpostosImpl();
    }

    /*
     * ==== Especificação dos Casos de Teste ====
     * * Item 5.b: A classe que implementa o cálculo do imposto
     * Item 5.d: A classe que implementa o "Serviço de impostos"
     * * Regra de Negócio: O imposto é de 10% sobre o subtotal.
     * * Caso de Teste 1: Valor Padrão
     * - Entrada: subTotal = 100.0
     * - Saída Esperada: 10.0
     * * Caso de Teste 2: Valor Zero
     * - Entrada: subTotal = 0.0
     * - Saída Esperada: 0.0
     * * Caso de Teste 3: Valor Quebrado
     * - Entrada: subTotal = 55.50
     * - Saída Esperada: 5.55
     */
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