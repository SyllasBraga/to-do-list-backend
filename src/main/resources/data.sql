create table usuario(
id int not null auto_increment,
nome varchar(255) not null,
login varchar(255) unique not null,
senha varchar(255) not null,
primary key(id)
);

create table tarefa(
id int not null auto_increment,
descricao varchar(255) not null,
status_tarefa varchar(255) not null,
data_cadastro TIMESTAMP not null,
data_prazo TIMESTAMP,
data_termino TIMESTAMP,
usuario int not null,
primary key(id),
CONSTRAINT usuario_id_fk FOREIGN KEY (usuario) REFERENCES usuario(id)
);

insert into usuario(nome, login, senha) values ('Joao', 'joao', '123');
insert into usuario(nome, login, senha) values ('Maria', 'maria', '123');

insert into tarefa(descricao, status_tarefa, data_cadastro, data_prazo, data_termino, usuario) values ('Ler dez páginas de Clean Code', 'PENDENTE', '2021-12-07 15:00:00','2021-11-29 15:00:00', null, 1); 
insert into tarefa(descricao, status_tarefa, data_cadastro, data_prazo, data_termino, usuario) values ('Cortar o cabelo', 'CONCLUIDA', '2023-01-01 15:00:00', '2023-01-06 08:00:00','2023-01-04 08:55:00', 1);
insert into tarefa(descricao, status_tarefa, data_cadastro, data_prazo, data_termino, usuario) values ('Pagar os boletos', 'VENCIDA', '2023-01-03 15:00:00', '2023-01-04 08:00:00', null, 1);

insert into tarefa(descricao, status_tarefa, data_cadastro, data_prazo, data_termino, usuario) values ('Estudar array functions - JavaScript', 'CONCLUIDA', '2023-07-01 15:00:00', '2023-07-15 08:00:00', '2023-07-07 08:55:00',2);
insert into tarefa(descricao, status_tarefa, data_cadastro, data_prazo, data_termino, usuario) values ('Terminar de assistir curso de Spring', 'PENDENTE', '2023-07-01 15:00:00', '2023-07-07 08:55:00',null, 2);
insert into tarefa(descricao, status_tarefa, data_cadastro, data_prazo, data_termino, usuario) values ('Terminar trabalho de AEDS', 'VENCIDA', '2023-06-06', '2023-07-06',null, 2);
