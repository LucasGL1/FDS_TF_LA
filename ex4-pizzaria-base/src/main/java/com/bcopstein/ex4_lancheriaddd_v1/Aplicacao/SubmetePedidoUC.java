package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.StatusPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoDescontos;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoEstoque;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoImpostos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SubmetePedidoUC {
    private final PedidoRepository pedidos;
    private final ProdutosRepository produtos;
    private final ServicoEstoque servicoEstoque;
    private final ServicoImpostos servicoImpostos;
    private final ServicoDescontos servicoDescontos;

    @Autowired
    public SubmetePedidoUC(PedidoRepository pedidos, ProdutosRepository produtos, ServicoEstoque servicoEstoque, ServicoImpostos servicoImpostos, ServicoDescontos servicoDescontos) {
        this.pedidos = pedidos;
        this.produtos = produtos;
        this.servicoEstoque = servicoEstoque;
        this.servicoImpostos = servicoImpostos;
        this.servicoDescontos = servicoDescontos;
    }

    public Pedido run(Pedido pedido) {
        boolean estoqueOk = servicoEstoque.verificaDisponibilidade(pedido);
        if (!estoqueOk) {
            // Se não houver estoque, retorna o pedido sem aprovação
            return pedido;
        }

        // Calcula o subtotal do pedido
        Map<Long, Produto> produtosMap = produtos.findAll().stream()
                .collect(Collectors.toMap(Produto::getId, p -> p));

        double subTotal = 0.0;
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtosMap.get(item.getProdutoId());
            if (produto != null) {
                // Preço do produto está em centavos, dividimos por 100 para ter o valor real
                subTotal += item.getQuantidade() * (produto.getPreco() / 100.0);
            }
        }
        
        long clienteIdSimulado = 1L;
        double percentualDesconto = servicoDescontos.calculaDesconto(clienteIdSimulado);
        double valorDesconto = subTotal * percentualDesconto;
        
        double subTotalComDesconto = subTotal - valorDesconto;
        
        double valorImposto = servicoImpostos.calculaImposto(subTotalComDesconto);

        double custoFinal = subTotalComDesconto + valorImposto;

        pedido.setCustoTotal(custoFinal);
        pedido.setStatus(StatusPedido.APROVADO);
        
        return pedidos.save(pedido);
    }
}