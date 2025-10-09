//OK

package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

@Component
public class CarregaCardapioUC {
    private ProdutosRepository produtos;

    @Autowired
    public CarregaCardapioUC(ProdutosRepository produtos) {
        this.produtos = produtos;
    }

    public List<Produto> run() {
        long cardapioVigenteId = 1L;
        return produtos.recuperaProdutosCardapio(cardapioVigenteId);
    }
}