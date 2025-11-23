package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.Descontos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EstrategiaClienteFrequenteTest {

    @Mock
    private PedidoRepository pedidoRepository; 

    @Mock
    private Pedido pedido;
    
    @Mock
    private Cliente cliente; 

    @InjectMocks
    private EstrategiaClienteFrequente estrategia; 

    @BeforeEach
    public void setup() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);
        
        when(pedido.getCliente()).thenReturn(cliente);
        when(cliente.getId()).thenReturn(1L);
    }

    /*
     * ==== Especificação dos Casos de Teste ====
     * * Item 5.a: Estratégia de desconto "ClienteFrequente"
     * Regra de Negócio: 7% de desconto se > 3 pedidos nos últimos 20 dias.
     * * Caso de Teste 1: Deve Aplicar Desconto
     * - Contexto: Repositório retorna 4 pedidos recentes.
     * - Saída Esperada: 0.07 (7%)
     * * Caso de Teste 2: Não Deve Aplicar Desconto
     * - Contexto: Repositório retorna 2 pedidos recentes.
     * - Saída Esperada: 0.0 (0%)
     */

    @Test
    @DisplayName("Deve aplicar 7% de desconto se o cliente for frequente")
    public void testeDeveAplicarDesconto() {
        List<Pedido> pedidosMock = List.of(new Pedido(), new Pedido(), new Pedido(), new Pedido());
        when(pedidoRepository.findPedidosRecentesByCliente(1L, 20)).thenReturn(pedidosMock);

        double desconto = estrategia.getPercentualDesconto(pedido);

        assertEquals(0.07, desconto);
    }

    @Test
    @DisplayName("Não deve aplicar desconto se o cliente não for frequente")
    public void testeNaoDeveAplicarDesconto() {
        List<Pedido> pedidosMock = List.of(new Pedido(), new Pedido());
        when(pedidoRepository.findPedidosRecentesByCliente(1L, 20)).thenReturn(pedidosMock);

        double desconto = estrategia.getPercentualDesconto(pedido);

        assertEquals(0.0, desconto);
    }
}