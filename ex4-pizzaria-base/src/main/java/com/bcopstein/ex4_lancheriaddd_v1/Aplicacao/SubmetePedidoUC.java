package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoDescontos;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoEstoque;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoImpostos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubmetePedidoUC {
    private final PedidoRepository pedidos;
    private final ServicoEstoque servicoEstoque;
    private final ServicoImpostos servicoImpostos;
    private final ServicoDescontos servicoDescontos;

    @Autowired
    public SubmetePedidoUC(PedidoRepository pedidos, ServicoEstoque servicoEstoque, ServicoImpostos servicoImpostos, ServicoDescontos servicoDescontos) {
        this.pedidos = pedidos;
        this.servicoEstoque = servicoEstoque;
        this.servicoImpostos = servicoImpostos;
        this.servicoDescontos = servicoDescontos;
    }

    public Pedido run(Pedido pedido) {
        boolean estoqueOk = servicoEstoque.verificaDisponibilidade(pedido);
        if (!estoqueOk) {
            return pedido;
        }

        double subTotal = 0.0;
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getItem(); 
            if (produto != null) {
                subTotal += item.getQuantidade() * (produto.getPreco() / 100.0);
            }
        }
        
        double percentualDesconto = servicoDescontos.calculaDesconto(pedido.getCliente().getId());
        double valorDesconto = subTotal * percentualDesconto;
        double subTotalComDesconto = subTotal - valorDesconto;
        double valorImposto = servicoImpostos.calculaImposto(subTotalComDesconto);
        double custoFinal = subTotalComDesconto + valorImposto;

        pedido.setValor(subTotal);
        pedido.setDesconto(valorDesconto);
        pedido.setImpostos(valorImposto);
        pedido.setValorCobrado(custoFinal);
        pedido.setStatus(Pedido.Status.APROVADO);
        
        return pedidos.save(pedido);
    }
}