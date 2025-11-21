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

public class EstrategiaClienteGastadorTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private Pedido pedido;
    
    @Mock
    private Cliente cliente;

    @InjectMocks
    private EstrategiaClienteGastador estrategia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(pedido.getCliente()).thenReturn(cliente);
        when(cliente.getId()).thenReturn(1L);
    }

    /*
     * ==== Especificação dos Casos de Teste ====
     * * Item 5.a: Estratégia de desconto "ClienteGastador"
     * Regra de Negócio: 15% de desconto se > R$ 500,00 gastos nos últimos 30 dias.
     * * Caso de Teste 1: Deve Aplicar Desconto
     * - Contexto: Repositório retorna 2 pedidos que somam R$ 600,00.
     * - Saída Esperada: 0.15 (15%)
     * * Caso de Teste 2: Não Deve Aplicar Desconto
     * - Contexto: Repositório retorna pedidos que somam R$ 300,00.
     * - Saída Esperada: 0.0 (0%)
     */

    @Test
    @DisplayName("Deve aplicar 15% de desconto se o cliente for gastador")
    public void testeDeveAplicarDesconto() {
        Pedido p1 = new Pedido(); p1.setValorCobrado(300.0);
        Pedido p2 = new Pedido(); p2.setValorCobrado(300.0);
        List<Pedido> pedidosMock = List.of(p1, p2);
        when(pedidoRepository.findPedidosRecentesByCliente(1L, 30)).thenReturn(pedidosMock);

        double desconto = estrategia.getPercentualDesconto(pedido);

        assertEquals(0.15, desconto);
    }

    @Test
    @DisplayName("Não deve aplicar desconto se o cliente não for gastador")
    public void testeNaoDeveAplicarDesconto() {
        Pedido p1 = new Pedido(); p1.setValorCobrado(100.0);
        List<Pedido> pedidosMock = List.of(p1);
        when(pedidoRepository.findPedidosRecentesByCliente(1L, 30)).thenReturn(pedidosMock);

        double desconto = estrategia.getPercentualDesconto(pedido);

        assertEquals(0.0, desconto);
    }
}