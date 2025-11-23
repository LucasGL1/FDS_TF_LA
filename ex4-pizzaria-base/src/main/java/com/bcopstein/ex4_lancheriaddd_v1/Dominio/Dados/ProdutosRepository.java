package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProdutosRepository extends JpaRepository<Produto, Long> {
    
    // Esta query vai buscar na tabela de junção 'cardapio_produto'
    @Query(value = "SELECT p.* FROM produtos p " +
                   "JOIN cardapio_produto cp ON p.id = cp.produto_id " +
                   "WHERE cp.cardapio_id = :cardapioId", nativeQuery = true)
    List<Produto> findByCardapioId(@Param("cardapioId") Long cardapioId);
}