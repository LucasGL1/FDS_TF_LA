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
    private ConfiguradorCardapio configurador; // <-- NOVA DEPENDÊNCIA

    @Autowired
    public CarregaCardapioUC(ProdutosRepository produtos, ConfiguradorCardapio configurador) { // <-- DEPENDÊNCIA ADICIONADA
        this.produtos = produtos;
        this.configurador = configurador; // <-- DEPENDÊNCIA ADICIONADA
    }

    public List<ProdutoCardapioDTO> run() {
        // LÓGICA ATUALIZADA
        // 1. Pega o ID do cardápio que o "master" definiu
        long cardapioVigenteId = configurador.getIdCardapioAtivo();
        
        // 2. Busca as Entidades de Produto (com receita) do banco
        List<Produto> produtosEntidade = produtos.recuperaProdutosCardapio(cardapioVigenteId);

        // 3. Converte a lista de Entidades para uma lista de DTOs (sem receita)
        return produtosEntidade.stream()
                .map(produto -> new ProdutoCardapioDTO(
                        produto.getId(),
                        produto.getDescricao(),
                        produto.getPreco()
                ))
                .collect(Collectors.toList());
    }
}