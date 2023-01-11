drop database if exists to_do_list;
create database to_do_list;
use to_do_list;

create table usuario(
id int primary key not null auto_increment,
nome varchar(255) not null,
login varchar(255) unique not null,
senha varchar(255) not null
);

create table tarefa(
id int not null primary key auto_increment,
descricao varchar(255) not null,
status_tarefa varchar(255) not null,
data_cadastro date not null,
data_prazo date,
data_termino date,
usuario int not null
);

alter table tarefa
add foreign key fk_usuario(usuario) references usuario(id);

insert into usuario(nome, login, senha) values ("João", "joão", "123"), ("Maria", "maria", "123");

insert into tarefa(descricao, status_tarefa, data_cadastro, data_prazo, data_termino, usuario) values ("Ler dez páginas de Clean Code", "PENDENTE", '2021-12-07 15:00:00','2021-11-29 15:00:00', null, 1), 
("Cortar o cabelo", "CONCLUIDA", '2023-01-01 15:00:00', "2023-01-06 08:00:00","2023-01-04 08:55:00", 1), ("Pagar os boletos", "VENCIDA", '2023-01-03 15:00:00', "2023-01-04 08:00:00", null, 1);

insert into tarefa(descricao, status_tarefa, data_cadastro, data_prazo, data_termino, usuario) values ("Estudar array functions - JavaScript", "CONCLUIDA", '2023-07-01 15:00:00', "2023-07-15 08:00:00", "2023-07-07 08:55:00",2),
("Terminar de assistir curso de Spring", "Pendente", '2023-07-01 15:00:00', "2023-07-07 08:55:00",null, 2), ("Terminar trabalho de AEDS", "VENCIDA", '2023-06-06', "2023-07-06",null, 2);