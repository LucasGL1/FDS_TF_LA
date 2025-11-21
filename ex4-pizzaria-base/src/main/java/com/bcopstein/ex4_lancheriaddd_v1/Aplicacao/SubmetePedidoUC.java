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
        boolean estoqueOk = servicoEstoque.verificaDisponibilidade(pedido);
        if (!estoqueOk) {
            return null; 
        }

        Cliente clienteCompleto = clienteRepository.findById(pedido.getCliente().getId());
        pedido.setCliente(clienteCompleto);
        for (ItemPedido item : pedido.getItens()) {
            Produto produtoCompleto = produtosRepository.recuperaProdutoPorid(item.getItem().getId());
            item.setItem(produtoCompleto);
        }

        double subTotal = 0.0;
        for (ItemPedido item : pedido.getItens()) {
            subTotal += item.getQuantidade() * (item.getItem().getPreco() / 100.0);
        }
        
        double percentualDesconto = servicoDescontos.getPercentualDesconto(pedido);
        
        double valorDesconto = subTotal * percentualDesconto;
        double subTotalComDesconto = subTotal - valorDesconto;
        double valorImposto = servicoImpostos.calculaImposto(subTotalComDesconto);
        double custoFinal = subTotalComDesconto + valorImposto;

        // 4. ATUALIZAR E SALVAR
        pedido.setValor(subTotal);
        pedido.setDesconto(valorDesconto);
        pedido.setImpostos(valorImposto);
        pedido.setValorCobrado(custoFinal);
        pedido.setStatus(Pedido.Status.APROVADO);
        
        Pedido pedidoSalvo = pedidos.save(pedido);
        
        return new PedidoResponseDTO(pedidoSalvo); 
    }
}