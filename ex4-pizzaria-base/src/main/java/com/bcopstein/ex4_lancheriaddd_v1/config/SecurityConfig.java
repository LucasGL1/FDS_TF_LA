package com.bcopstein.ex4_lancheriaddd_v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // REGRA 1: Permite o UC1 (Carregar Cardápio) sem login
                .requestMatchers(HttpMethod.GET, "/pedidos/cardapio").permitAll()
                
                // REGRA 2: Permite o Registro de Cliente
                .requestMatchers(HttpMethod.POST, "/clientes/registrar").permitAll()
                
                // REGRA 3: Permite o acesso ao Console H2
                .requestMatchers(HttpMethod.GET, "/h2/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/h2/**").permitAll()

                // REGRA 4 (UC Master Cardápio): Apenas ADMIN
                .requestMatchers(HttpMethod.POST, "/pedidos/cardapio/ativar/**").hasRole("ADMIN")

                // REGRA NOVA (UC Master Desconto): Apenas ADMIN
                .requestMatchers(HttpMethod.POST, "/pedidos/desconto/ativar").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}