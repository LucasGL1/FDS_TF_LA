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

// IMPORTS QUE FALTAVAM
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubmetePedidoUC {
    private final PedidoRepository pedidos;
    private final ServicoEstoque servicoEstoque;
    private final ServicoImpostos servicoImpostos;
    private final ServicoDescontos servicoDescontos;
    private final ClienteRepository clienteRepository;
    private final ProdutosRepository produtosRepository;

    @Autowired
    public SubmetePedidoUC(PedidoRepository pedidos, ServicoEstoque servicoEstoque, 
                           ServicoImpostos servicoImpostos, ServicoDescontos servicoDescontos,
                           ClienteRepository clienteRepository, ProdutosRepository produtosRepository) {
        this.pedidos = pedidos;
        this.servicoEstoque = servicoEstoque;
        this.servicoImpostos = servicoImpostos;
        this.servicoDescontos = servicoDescontos;
        this.clienteRepository = clienteRepository;
        this.produtosRepository = produtosRepository;
    }

    public PedidoResponseDTO run(Pedido pedido) {
        // --- PASSO 1: CARREGAR DADOS ---
        // Carrega o cliente do banco
        Cliente clienteCompleto = clienteRepository.findById(pedido.getCliente().getId());
        if (clienteCompleto == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        pedido.setCliente(clienteCompleto);

        // RECONSTRUIR A LISTA DE ITENS COM PRODUTOS DO BANCO
        // Isso garante que 'item.getItem()' nunca seja nulo e tenha a receita carregada
        List<ItemPedido> itensCompletos = new ArrayList<>();
        
        for (ItemPedido item : pedido.getItens()) {
            // O JSON manda apenas o ID do produto dentro do objeto item.item
            long idProduto = item.getItem().getId();
            
            // Busca o produto completo no banco (com preço e receita)
            // O .orElse(null) evita erro se o produto não existir, mas tratamos logo abaixo
            Produto produtoCompleto = produtosRepository.findById(idProduto).orElse(null);
            
            if (produtoCompleto == null) {
                throw new IllegalArgumentException("Produto " + idProduto + " não encontrado");
            }
            
            // Cria um novo objeto ItemPedido com o produto carregado e a quantidade original
            ItemPedido itemAtualizado = new ItemPedido();
            itemAtualizado.setItem(produtoCompleto);
            itemAtualizado.setQuantidade(item.getQuantidade());
            
            itensCompletos.add(itemAtualizado);
        }
        
        // Substitui a lista original (que veio do JSON incompleta) pela lista completa
        pedido.setItens(itensCompletos);

        // --- PASSO 2: VERIFICAR ESTOQUE ---
        // Agora o 'pedido' tem os produtos e receitas, então o serviço consegue checar
        boolean estoqueOk = servicoEstoque.verificaDisponibilidade(pedido);
        if (!estoqueOk) {
            return null; // Pedido negado (retorna 200 OK com corpo vazio)
        }

        // --- PASSO 3: CÁLCULOS ---
        double subTotal = 0.0;
        for (ItemPedido item : pedido.getItens()) {
            // Agora é impossível dar NullPointerException aqui
            subTotal += item.getQuantidade() * (item.getItem().getPreco() / 100.0);
        }
        
        double percentualDesconto = servicoDescontos.getPercentualDesconto(pedido);
        double valorDesconto = subTotal * percentualDesconto;
        double subTotalComDesconto = subTotal - valorDesconto;
        double valorImposto = servicoImpostos.calculaImposto(subTotalComDesconto);
        double custoFinal = subTotalComDesconto + valorImposto;

        // --- PASSO 4: FINALIZAR ---
        pedido.setValor(subTotal);
        pedido.setDesconto(valorDesconto);
        pedido.setImpostos(valorImposto);
        pedido.setValorCobrado(custoFinal);
        pedido.setStatus(Pedido.Status.APROVADO);
        
        Pedido pedidoSalvo = pedidos.save(pedido);
        
        return new PedidoResponseDTO(pedidoSalvo); 
    }
}