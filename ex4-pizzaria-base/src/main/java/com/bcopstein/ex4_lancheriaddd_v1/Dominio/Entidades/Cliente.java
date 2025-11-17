package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

public class Cliente {
    private long id;
    private String cpf;
    private String nome;
    private String celular;
    private String endereco;
    private String email;
    private String senha;
    private String role; // <-- CAMPO ADICIONADO

    public Cliente() {} // Construtor vazio

    // Construtor principal
    public Cliente(long id, String cpf, String nome, String celular, String endereco, String email) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.celular = celular;
        this.endereco = endereco;
        this.email = email;
    }

    // Getters
    public long getId() { return id; }
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getCelular() { return celular; }
    public String getEndereco() { return endereco; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getRole() { return role; } // <-- GETTER ADICIONADO

    // Setters
    public void setId(long id) { this.id = id; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCelular(String celular) { this.celular = celular; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setRole(String role) { this.role = role; } // <-- SETTER ADICIONADO
}