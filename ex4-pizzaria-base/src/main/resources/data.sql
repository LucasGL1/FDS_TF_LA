-- Clientes
INSERT INTO clientes (id, cpf, nome, celular, endereco, email, senha, role) VALUES (1, '9001', 'Huguinho Pato', '51985744566', 'Rua das Flores, 100', 'huguinho.pato@email.com', 'senhaExemplo123', 'USER');
INSERT INTO clientes (id, cpf, nome, celular, endereco, email, senha, role) VALUES (2, '9002', 'Luizinho Pato', '5199172079', 'Av. Central, 200', 'zezinho.pato@email.com', 'senhaExemplo456', 'USER');
INSERT INTO clientes (id, cpf, nome, celular, endereco, email, senha, role) VALUES (99, '000.000.000-00', 'Master User', '000000000', 'Admin', 'master@admin.com', '$2a$10$gGCNp6mAXAwKrISF0DzFFOcGcE8JLWHvOjfBTAo.qq8uyXZpvVwoe', 'ADMIN');

-- Ingredientes
INSERT INTO ingredientes (id, descricao) VALUES (1, 'Disco de pizza');
INSERT INTO ingredientes (id, descricao) VALUES (2, 'Porcao de tomate');
INSERT INTO ingredientes (id, descricao) VALUES (3, 'Porcao de mussarela');
INSERT INTO ingredientes (id, descricao) VALUES (4, 'Porcao de presunto');
INSERT INTO ingredientes (id, descricao) VALUES (5, 'Porcao de calabresa');
INSERT INTO ingredientes (id, descricao) VALUES (6, 'Molho de tomate (200ml)');
INSERT INTO ingredientes (id, descricao) VALUES (7, 'Porcao de azeitona');
INSERT INTO ingredientes (id, descricao) VALUES (8, 'Porcao de oregano');
INSERT INTO ingredientes (id, descricao) VALUES (9, 'Porcao de cebola');

-- Estoque
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (1, 30, 1);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (2, 30, 2);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (3, 30, 3);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (4, 30, 4);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (5, 30, 5);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (6, 30, 6);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (7, 30, 7);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (8, 30, 8);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (9, 30, 9);

-- Receitas
INSERT INTO receitas (id, titulo) VALUES (1, 'Pizza calabresa');
INSERT INTO receitas (id, titulo) VALUES (2, 'Pizza queijo e presunto');
INSERT INTO receitas (id, titulo) VALUES (3, 'Pizza margherita');

-- Receita Ingredientes
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 1);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 6);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 3);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 5);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 1);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 6);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 3);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 4);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 1);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 6);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 3);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 8);

-- Produtos (COM RECEITA_ID)
INSERT INTO produtos (id, descricao, preco, receita_id) VALUES (1, 'Pizza calabresa', 5500, 1);
INSERT INTO produtos (id, descricao, preco, receita_id) VALUES (2, 'Pizza queijo e presunto', 6000, 2);
INSERT INTO produtos (id, descricao, preco, receita_id) VALUES (3, 'Pizza margherita', 4000, 3);

-- Cardapios
INSERT INTO cardapios (id, titulo) VALUES (1, 'Cardapio de Agosto');
INSERT INTO cardapios (id, titulo) VALUES (2, 'Cardapio de Setembro');

-- Cardapio Produtos
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (1, 1);
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (1, 2);
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (1, 3);
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (2, 1);
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (2, 3);