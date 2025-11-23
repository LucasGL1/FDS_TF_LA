package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.CardapioRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.CabecalhoCardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cardapio;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

@Component
public class CardapioRepositoryJDBC implements CardapioRepository {
    private JdbcTemplate jdbcTemplate;
    private ProdutosRepository produtosRepository;

    @Autowired
    public CardapioRepositoryJDBC(JdbcTemplate jdbcTemplate, ProdutosRepository produtosRepository){
        this.jdbcTemplate = jdbcTemplate;
        this.produtosRepository = produtosRepository;
    }

    @Override
    public Cardapio recuperaPorId(long id) {
        // Busca os cabeçalhos (se existirem)
        String sql = "SELECT id, titulo FROM cardapios WHERE id = ?";
        List<Cardapio> cardapios = this.jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            (rs, rowNum) -> new Cardapio(rs.getLong("id"), rs.getString("titulo"), null)
        );
        if (cardapios.isEmpty()) {
            return null;
        }
        Cardapio cardapio = cardapios.get(0);
        
        // CORREÇÃO: Usa findAll() do JPA em vez de recuperaProdutosCardapio
        List<Produto> produtos = produtosRepository.findAll();
        
        cardapio.setProdutos(produtos);
        return cardapio;
    }

    @Override
    public List<Produto> indicacoesDoChef() {
        // CORREÇÃO: Usa findById() do JPA em vez de recuperaProdutoPorid
        // Retorna o produto 2 ou null se não achar
        Produto prod = produtosRepository.findById(2L).orElse(null);
        return (prod != null) ? List.of(prod) : List.of();
    }

    @Override
    public List<CabecalhoCardapio> cardapiosDisponiveis(){
        String sql = "SELECT id, titulo FROM cardapios";
        return this.jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new CabecalhoCardapio(rs.getLong("id"), rs.getString("titulo"))
        );
    }
}