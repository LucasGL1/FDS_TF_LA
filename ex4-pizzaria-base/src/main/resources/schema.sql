drop table if exists item_pedido;
drop table if exists pedidos;
drop table if exists cardapio_produto;
drop table if exists cardapios;
drop table if exists produto_receita;
drop table if exists produtos;
drop table if exists receita_ingrediente;
drop table if exists receitas;
drop table if exists itensEstoque;
drop table if exists ingredientes;
drop table if exists clientes;

create table clientes(
    id bigint not null primary key,
    cpf varchar(15) not null,
    nome varchar(100) not null,
    celular varchar(20) not null,
    endereco varchar(255) not null,
    email varchar(255) not null,
    senha varchar(255) not null,
    role varchar(50) not null
);

create table ingredientes (
    id bigint primary key,
    descricao varchar(255) not null
);

-- (Mantenha as outras tabelas iguais, altere apenas esta)

create table itens_estoque(  -- <--- MUDOU O NOME
    id bigint primary key,
    quantidade int,
    ingrediente_id bigint,
    foreign key (ingrediente_id) references ingredientes(id)
);

create table receitas (
    id bigint primary key,
    titulo varchar(255) not null
);

create table receita_ingrediente (
    receita_id bigint not null,
    ingrediente_id bigint not null,
    primary key (receita_id, ingrediente_id),
    foreign key (receita_id) references receitas(id),
    foreign key (ingrediente_id) references ingredientes(id)
);

-- TABELA PRODUTOS COM RECEITA_ID
create table produtos (
    id bigint primary key,
    descricao varchar(255) not null,
    preco bigint,
    receita_id bigint, 
    foreign key (receita_id) references receitas(id)
);

create table cardapios (
    id bigint primary key,
    titulo varchar(255) not null
);

create table cardapio_produto (
    cardapio_id bigint not null,
    produto_id bigint not null,
    primary key (cardapio_id,produto_id),
    foreign key (cardapio_id) references cardapios(id),
    foreign key (produto_id) references produtos(id)
);

create table pedidos (
    id bigint primary key,
    cliente_id bigint not null,
    data_hora_pagamento timestamp,
    status varchar(50) not null,
    valor double not null,
    impostos double not null,
    desconto double not null,
    valor_cobrado double not null,
    foreign key (cliente_id) references clientes(id)
);

create table item_pedido (
    id bigint auto_increment primary key,
    pedido_id bigint not null,
    produto_id bigint not null,
    quantidade int not null,
    foreign key (pedido_id) references pedidos(id),
    foreign key (produto_id) references produtos(id)
);