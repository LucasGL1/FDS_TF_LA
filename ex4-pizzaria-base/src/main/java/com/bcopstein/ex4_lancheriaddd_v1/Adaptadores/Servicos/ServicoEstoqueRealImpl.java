package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Servicos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ItemEstoqueRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ReceitasRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Receita;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoEstoque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ServicoEstoqueRealImpl implements ServicoEstoque {

    private ItemEstoqueRepository estoqueRepo;
    private ReceitasRepository receitasRepo;

    @Autowired
    public ServicoEstoqueRealImpl(ItemEstoqueRepository estoqueRepo, ReceitasRepository receitasRepo) {
        this.estoqueRepo = estoqueRepo;
        this.receitasRepo = receitasRepo;
    }

    @Override
    public boolean verificaDisponibilidade(Pedido pedido) {
        for (ItemPedido itemPedido : pedido.getItens()) {
            Produto produto = itemPedido.getItem();
            
            if (produto == null) continue;

            Receita receita = receitasRepo.recuperaReceita(produto.getReceita().getId());
            if (receita == null) {
                return false; 
            }

            for (Ingrediente ingrediente : receita.getIngredientes()) {
                Optional<ItemEstoque> itemEstoqueOpt = estoqueRepo.findByIngrediente(ingrediente);

                if (!itemEstoqueOpt.isPresent()) {
                    return false; 
                }

                ItemEstoque itemEstoque = itemEstoqueOpt.get();
                int quantidadeNecessaria = itemPedido.getQuantidade();
                
                if (itemEstoque.getQuantidade() < quantidadeNecessaria) {
                    return false; 
                }
            }
        }
        
        return true;
    }
}