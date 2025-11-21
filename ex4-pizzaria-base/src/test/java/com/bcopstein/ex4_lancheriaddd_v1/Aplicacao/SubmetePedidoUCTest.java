package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponseDTO;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClienteRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoDescontos;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoEstoque;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoImpostos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*; 
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SubmetePedidoUCTest {

    @Mock private PedidoRepository pedidosRepo;
    @Mock private ServicoEstoque servicoEstoque;
    @Mock private ServicoImpostos servicoImpostos;
    @Mock private ServicoDescontos servicoDescontos;
    @Mock private ClienteRepository clienteRepo;
    @Mock private ProdutosRepository produtosRepo;

    @InjectMocks
    private SubmetePedidoUC submetePedidoUC; 

    @Mock private Pedido pedido;
    @Mock private Cliente cliente;
    @Mock private ItemPedido itemPedido;
    @Mock private Produto produto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        when(pedido.getCliente()).thenReturn(cliente);
        when(cliente.getId()).thenReturn(1L);
        when(pedido.getItens()).thenReturn(List.of(itemPedido));
        when(itemPedido.getItem()).thenReturn(produto);
        when(produto.getId()).thenReturn(10L);
        when(produto.getPreco()).thenReturn(5000); // R$ 50,00
        when(itemPedido.getQuantidade()).thenReturn(2);
    }

    /*
     * ==== Especificação dos Casos de Teste ====
     * * Item 5.e: A classe que implementa o caso de uso "Submeter Pedido"
     * Regra de Negócio: Deve orquestrar a validação de estoque, carregamento de
     * entidades, cálculo de custos (subtotal, desconto, imposto) e salvar o pedido.
     * * Caso de Teste 1: Caminho Feliz (Pedido Aprovado com Desconto)
     * - Contexto: Estoque OK, Cliente e Produto existem.
     * - Ação: Submeter pedido.
     * - Saída Esperada: Pedido DTO com status APROVADO, 
     * valorCobrado calculado corretamente, e desconto aplicado.
     * * Caso de Teste 2: Caminho Triste (Sem Estoque)
     * - Contexto: ServicoEstoque retorna false.
     * - Ação: Submeter pedido.
     * - Saída Esperada: null (ou DTO de erro, dependendo da implementação).
     */

    @Test
    @DisplayName("Deve aprovar, calcular e salvar um pedido válido")
    public void testeCaminhoFeliz() {
        when(servicoEstoque.verificaDisponibilidade(any(Pedido.class))).thenReturn(true);
        when(clienteRepo.findById(1L)).thenReturn(cliente);
        when(produtosRepo.recuperaProdutoPorid(10L)).thenReturn(produto);
        when(servicoDescontos.getPercentualDesconto(any(Pedido.class))).thenReturn(0.07); // 7%
        when(servicoImpostos.calculaImposto(anyDouble())).thenReturn(9.30); // 10% de (100 - 7)
        when(pedidosRepo.save(any(Pedido.class))).thenReturn(pedido);

        PedidoResponseDTO response = submetePedidoUC.run(pedido);

        assertNotNull(response);
        assertEquals(Pedido.Status.APROVADO, response.getStatus());

        assertEquals(102.30, response.getValorTotal(), 0.001);
        assertEquals(7.0, response.getDesconto());

        verify(pedidosRepo, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Deve negar um pedido se não houver estoque")
    public void testeCaminhoSemEstoque() {
        when(servicoEstoque.verificaDisponibilidade(any(Pedido.class))).thenReturn(false);

        PedidoResponseDTO response = submetePedidoUC.run(pedido);

        assertNull(response); 
        
        verify(pedidosRepo, never()).save(any(Pedido.class)); 
    }
}