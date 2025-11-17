create table if not exists clientes(
    id bigint not null primary key,
    cpf varchar(15) not null,
    nome varchar(100) not null,
    celular varchar(20) not null,
    endereco varchar(255) not null,
    email varchar(255) not null,
    senha varchar(255) not null,
    role varchar(50) not null -- <-- ADICIONE ESTA LINHA
);

create table if not exists ingredientes (
    id bigint primary key,
    descricao varchar(255) not null
);

create table if not exists itensEstoque(
    id bigint primary key,
    quantidade int,
    ingrediente_id bigint,
    foreign key (ingrediente_id) references ingredientes(id)
);

create table if not exists receitas (
    id bigint primary key,
    titulo varchar(255) not null
);

create table if not exists receita_ingrediente (
    receita_id bigint not null,
    ingrediente_id bigint not null,
    primary key (receita_id, ingrediente_id),
    foreign key (receita_id) references receitas(id),
    foreign key (ingrediente_id) references ingredientes(id)
);

create table if not exists produtos (
    id bigint primary key,
    descricao varchar(255) not null,
    preco bigint
);

create table if not exists produto_receita (
    produto_id bigint not null,
    receita_id bigint not null,
    primary key (produto_id,receita_id),
    foreign key (produto_id) references produtos(id),
    foreign key (receita_id) references receitas(id)
);

create table if not exists cardapios (
    id bigint primary key,
    titulo varchar(255) not null
);

create table if not exists cardapio_produto (
    cardapio_id bigint not null,
    produto_id bigint not null,
    primary key (cardapio_id,produto_id),
    foreign key (cardapio_id) references cardapios(id),
    foreign key (produto_id) references produtos(id)
);

-- Tabela PEDIDOS (NOVA)
create table if not exists pedidos (
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

-- Tabela ITEM_PEDIDO (NOVA)
create table if not exists item_pedido (
    id bigint auto_increment primary key,
    pedido_id bigint not null,
    produto_id bigint not null,
    quantidade int not null,
    foreign key (pedido_id) references pedidos(id),
    foreign key (produto_id) references produtos(id)
);