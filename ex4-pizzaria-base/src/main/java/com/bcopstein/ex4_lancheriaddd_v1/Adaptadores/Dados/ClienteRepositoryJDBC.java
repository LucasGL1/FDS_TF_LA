package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClienteRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository; 
import java.util.List;

@Repository // Esta anotação é crucial para o Spring encontrá-lo
public class ClienteRepositoryJDBC implements ClienteRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ClienteRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cliente findById(long id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        List<Cliente> clientes = jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            (rs, rowNum) -> new Cliente(
                rs.getLong("id"),
                rs.getString("cpf"),
                rs.getString("nome"),
                rs.getString("celular"),
                rs.getString("endereco"),
                rs.getString("email")
            )
        );
        return clientes.isEmpty() ? null : clientes.get(0);
    }
}