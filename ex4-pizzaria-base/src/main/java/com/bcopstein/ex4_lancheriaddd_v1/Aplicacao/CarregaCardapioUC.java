package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CarregaCardapioUC {
    private ProdutosRepository produtos;

    @Autowired
    public CarregaCardapioUC(ProdutosRepository produtos) {
        this.produtos = produtos;
    }

    public List<Produto> run() {
        return produtos.findAll();
    }
}