package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CardapioResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

public class CardapioPresenter {
    
    // Sua classe interna (não mudou)
    public class ItemCardapioPresenter {
        private Long id;
        private String descricao;
        private int preco;
        private boolean indicacao;
        
        public ItemCardapioPresenter(Long id, String descricao, int preco, boolean indicacao) {
            this.id = id;
            this.descricao = descricao;
            this.preco = preco;
            this.indicacao = indicacao;
        }

        public Long getId() { return id; }
        public String getDescricao() { return descricao; }
        public int getPreco() { return preco; }
        public boolean isIndicacao() { return indicacao; }
    }

    private String titulo;
    private List<ItemCardapioPresenter> itens;

    
    public CardapioPresenter(CardapioResponse cardapioResponse) {
        // Define o título e inicializa a lista (como no seu construtor original)
        this.titulo = cardapioResponse.getCardapio().getTitulo();
        this.itens = new LinkedList<>();

        Set<Long> conjIdSugestoes = cardapioResponse.getSugestoesDoChef().stream()
            .map(Produto::getId)
            .collect(Collectors.toSet());

        for(Produto produto : cardapioResponse.getCardapio().getProdutos()){
            boolean sugestao = conjIdSugestoes.contains(produto.getId());
            
            // 3. O método 'insereItem' agora é chamado internamente
            itens.add(new ItemCardapioPresenter(produto.getId(), produto.getDescricao(), produto.getPreco(), sugestao));
        }
    }

    public String getTitulo(){
        return titulo;
    }
    
    public List<ItemCardapioPresenter> getItens() {
        return itens;
    }
}