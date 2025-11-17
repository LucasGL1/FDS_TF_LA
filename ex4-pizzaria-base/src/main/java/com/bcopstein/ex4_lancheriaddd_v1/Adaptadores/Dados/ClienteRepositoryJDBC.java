package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClienteRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository; 
import java.util.List;

@Repository 
public class ClienteRepositoryJDBC implements ClienteRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ClienteRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Método auxiliar para mapear o resultado do banco para o objeto Cliente
    private Cliente mapRowToCliente(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCelular(rs.getString("celular"));
        cliente.setEndereco(rs.getString("endereco"));
        cliente.setEmail(rs.getString("email"));
        cliente.setSenha(rs.getString("senha"));
        cliente.setRole(rs.getString("role")); // <-- CAMPO ATUALIZADO
        return cliente;
    }

    @Override
    public Cliente findById(long id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        List<Cliente> clientes = jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            this::mapRowToCliente // Usa o método auxiliar
        );
        return clientes.isEmpty() ? null : clientes.get(0);
    }

    @Override
    public Cliente findByEmail(String email) {
        String sql = "SELECT * FROM clientes WHERE email = ?";
        List<Cliente> clientes = jdbcTemplate.query(
            sql,
            ps -> ps.setString(1, email),
            this::mapRowToCliente // Usa o método auxiliar
        );
        return clientes.isEmpty() ? null : clientes.get(0);
    }

    @Override
    public Cliente save(Cliente cliente) {
        // Define o role padrão como "USER" se não for especificado
        if (cliente.getRole() == null || cliente.getRole().isEmpty()) {
            cliente.setRole("USER");
        }
        
        String sql = "INSERT INTO clientes (id, cpf, nome, celular, endereco, email, senha, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql,
            cliente.getId(),
            cliente.getCpf(),
            cliente.getNome(),
            cliente.getCelular(),
            cliente.getEndereco(),
            cliente.getEmail(),
            cliente.getSenha(),
            cliente.getRole() // <-- CAMPO ATUALIZADO
        );
        return cliente; 
    }
}