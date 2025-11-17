package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClienteRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Imports necessários para as "Roles"
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // O "username" é o email
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new UsernameNotFoundException("Usuário (email) não encontrado: " + email);
        }

        // --- CÓDIGO ATUALIZADO AQUI ---
        // Cria a lista de "funções" (roles)
        List<GrantedAuthority> authorities = new ArrayList<>();
        // O Spring Security exige o prefixo "ROLE_"
        authorities.add(new SimpleGrantedAuthority("ROLE_" + cliente.getRole())); 

        // Constrói o User do Spring Security com as roles corretas
        return new User(cliente.getEmail(), cliente.getSenha(), authorities);
    }
}