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
import java.util.Optional; // IMPORT NECESSÁRIO

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

        // Configuração Comum dos Mocks
        when(pedido.getCliente()).thenReturn(cliente);
        when(cliente.getId()).thenReturn(1L);
        when(pedido.getItens()).thenReturn(List.of(itemPedido));
        
        // Mock do ItemPedido
        when(itemPedido.getItem()).thenReturn(produto);
        when(itemPedido.getQuantidade()).thenReturn(2);

        // Mock do Produto
        when(produto.getId()).thenReturn(10L);
        when(produto.getPreco()).thenReturn(5000); 
        
        // Mock dos Repositórios
        when(clienteRepo.findById(1L)).thenReturn(cliente);
        
        // MUDANÇA AQUI: Usamos findById em vez de recuperaProdutoPorid
        // O findById retorna um Optional, então usamos Optional.of(produto)
        when(produtosRepo.findById(10L)).thenReturn(Optional.of(produto));
    }

    @Test
    @DisplayName("Deve aprovar, calcular e salvar um pedido válido")
    public void testeCaminhoFeliz() {
        // Contexto Específico: Estoque OK
        when(servicoEstoque.verificaDisponibilidade(any(Pedido.class))).thenReturn(true);
        when(servicoDescontos.getPercentualDesconto(any(Pedido.class))).thenReturn(0.07); 
        when(servicoImpostos.calculaImposto(anyDouble())).thenReturn(9.30);
        when(pedidosRepo.save(any(Pedido.class))).thenReturn(pedido);
        when(pedido.getStatus()).thenReturn(Pedido.Status.APROVADO);

        // Ação
        PedidoResponseDTO response = submetePedidoUC.run(pedido);

        // Verificação
        assertNotNull(response, "A resposta não deveria ser nula (estoque ok)");
        assertEquals(Pedido.Status.APROVADO, response.getStatus());
        
        // Verifica se o save foi chamado
        verify(pedidosRepo, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Deve negar um pedido se não houver estoque")
    public void testeCaminhoSemEstoque() {
        // Contexto Específico: Sem Estoque
        when(servicoEstoque.verificaDisponibilidade(any(Pedido.class))).thenReturn(false);

        // Ação
        PedidoResponseDTO response = submetePedidoUC.run(pedido);

        // Verificação
        assertNull(response, "A resposta deveria ser nula quando não há estoque");
        verify(pedidosRepo, never()).save(any(Pedido.class)); 
    }
}