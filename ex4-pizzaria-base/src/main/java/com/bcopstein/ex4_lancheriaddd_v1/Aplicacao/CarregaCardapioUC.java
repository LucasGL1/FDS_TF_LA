package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.ProdutoCardapioDTO;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarregaCardapioUC {
    private ProdutosRepository produtos;
    private ConfiguradorCardapio configurador;

    @Autowired
    public CarregaCardapioUC(ProdutosRepository produtos, ConfiguradorCardapio configurador) {
        this.produtos = produtos;
        this.configurador = configurador;
    }

    public List<ProdutoCardapioDTO> run() {
        // 1. Pega o ID que o Master definiu (padrão é 1)
        long idAtivo = configurador.getIdCardapioAtivo();
        
        // 2. Busca APENAS os produtos desse cardápio
        List<Produto> produtosEntidade = produtos.findByCardapioId(idAtivo);

        return produtosEntidade.stream()
                .map(produto -> new ProdutoCardapioDTO(
                        produto.getId(),
                        produto.getDescricao(),
                        produto.getPreco()
                ))
                .collect(Collectors.toList());
    }
}