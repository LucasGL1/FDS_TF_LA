-- Inserção dos clientes (Corrigido para incluir o ID, SENHA e ROLE)
INSERT INTO clientes (id, cpf, nome, celular, endereco, email, senha, role) VALUES (1, '9001', 'Huguinho Pato', '51985744566', 'Rua das Flores, 100', 'huguinho.pato@email.com', 'senhaExemplo123', 'USER');
INSERT INTO clientes (id, cpf, nome, celular, endereco, email, senha, role) VALUES (2, '9002', 'Luizinho Pato', '5199172079', 'Av. Central, 200', 'zezinho.pato@email.com', 'senhaExemplo456', 'USER');
-- Usuário Master com a role ADMIN
INSERT INTO clientes (id, cpf, nome, celular, endereco, email, senha, role) VALUES (99, '000.000.000-00', 'Master User', '000000000', 'Admin', 'master@admin.com', '$2a$10$gGCNp6mAXAwKrISF0DzFFOcGcE8JLWHvOjfBTAo.qq8uyXZpvVwoe', 'ADMIN');

-- (O restante do seu data.sql, como ingredientes, produtos, etc., permanece o mesmo)
INSERT INTO ingredientes (id, descricao) VALUES (1, 'Disco de pizza');
INSERT INTO ingredientes (id, descricao) VALUES (2, 'Porcao de tomate');
INSERT INTO ingredientes (id, descricao) VALUES (3, 'Porcao de mussarela');
INSERT INTO ingredientes (id, descricao) VALUES (4, 'Porcao de presunto');
INSERT INTO ingredientes (id, descricao) VALUES (5, 'Porcao de calabresa');
INSERT INTO ingredientes (id, descricao) VALUES (6, 'Molho de tomate (200ml)');
INSERT INTO ingredientes (id, descricao) VALUES (7, 'Porcao de azeitona');
INSERT INTO ingredientes (id, descricao) VALUES (8, 'Porcao de oregano');
INSERT INTO ingredientes (id, descricao) VALUES (9, 'Porcao de cebola');

INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (1, 30, 1);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (2, 30, 2);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (3, 30, 3);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (4, 30, 4);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (5, 30, 5);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (6, 30, 6);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (7, 30, 7);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (8, 30, 8);
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (9, 30, 9);

INSERT INTO receitas (id, titulo) VALUES (1, 'Pizza calabresa');
INSERT INTO receitas (id, titulo) VALUES (2, 'Pizza queijo e presunto');
INSERT INTO receitas (id, titulo) VALUES (3, 'Pizza margherita');

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

INSERT INTO produtos (id,descricao,preco) VALUES (1,'Pizza calabresa', 5500);
INSERT INTO produtos (id,descricao,preco) VALUES (2,'Pizza queijo e presunto', 6000);
INSERT INTO produtos (id,descricao,preco) VALUES (3,'Pizza margherita', 4000);

INSERT INTO produto_receita (produto_id,receita_id) VALUES(1,1);
INSERT INTO produto_receita (produto_id,receita_id) VALUES(2,2);
INSERT INTO produto_receita (produto_id,receita_id) VALUES(3,3);

INSERT INTO cardapios (id,titulo) VALUES(1,'Cardapio de Agosto');
INSERT INTO cardapios (id,titulo) VALUES(2,'Cardapio de Setembro');

INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (1,1);
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (1,2);
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (1,3);
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (2,1);
INSERT INTO cardapio_produto (cardapio_id,produto_id) VALUES (2,3);

-- Pedidos de Teste
INSERT INTO pedidos (id, cliente_id, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) VALUES (1, 1, '2025-11-10T10:00:00', 'ENTREGUE', 50, 5, 0, 55);
INSERT INTO item_pedido (pedido_id, produto_id, quantidade) VALUES (1, 1, 1);

INSERT INTO pedidos (id, cliente_id, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) VALUES (2, 1, '2025-11-11T11:00:00', 'ENTREGUE', 60, 6, 0, 66);
INSERT INTO item_pedido (pedido_id, produto_id, quantidade) VALUES (2, 2, 1);

INSERT INTO pedidos (id, cliente_id, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) VALUES (3, 1, '2025-11-12T12:00:00', 'ENTREGUE', 40, 4, 0, 44);
INSERT INTO item_pedido (pedido_id, produto_id, quantidade) VALUES (3, 3, 1);

INSERT INTO pedidos (id, cliente_id, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) VALUES (4, 1, '2025-11-13T13:00:00', 'ENTREGUE', 70, 7, 0, 77);
INSERT INTO item_pedido (pedido_id, produto_id, quantidade) VALUES (4, 1, 1);
INSERT INTO item_pedido (pedido_id, produto_id, quantidade) VALUES (4, 3, 1);

INSERT INTO pedidos (id, cliente_id, data_hora_pagamento, status, valor, impostos, desconto, valor_cobrado) VALUES (101, 2, null, 'APROVADO', 100, 10, 0, 110);
INSERT INTO item_pedido (pedido_id, produto_id, quantidade) VALUES (101, 1, 1);
INSERT INTO item_pedido (pedido_id, produto_id, quantidade) VALUES (101, 3, 1);