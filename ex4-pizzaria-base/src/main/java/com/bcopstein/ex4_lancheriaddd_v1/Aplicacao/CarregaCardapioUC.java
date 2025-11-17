package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.ProdutoCardapioDTO; // Importa o DTO
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarregaCardapioUC {
    private ProdutosRepository produtos;

    @Autowired
    public CarregaCardapioUC(ProdutosRepository produtos) {
        this.produtos = produtos;
    }

    // O método agora retorna uma lista do nosso DTO
    public List<ProdutoCardapioDTO> run() {
        long cardapioVigenteId = 1L; // Conforme UC1
        
        // 1. Busca as Entidades de Produto (com receita) do banco
        List<Produto> produtosEntidade = produtos.recuperaProdutosCardapio(cardapioVigenteId);

        // 2. Converte a lista de Entidades para uma lista de DTOs (sem receita)
        return produtosEntidade.stream()
                .map(produto -> new ProdutoCardapioDTO(
                        produto.getId(),
                        produto.getDescricao(),
                        produto.getPreco()
                ))
                .collect(Collectors.toList());
    }
}