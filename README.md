# Código:


create database Aulabanco;
use Aulabanco;

create table tb_usuarioss(
id_usuario int primary key,
usuario varchar(50) not null,
login varchar(50) not null unique,
senha varchar(50) not null,
perfil varchar(15) not null
);


describe tb_usuarioss;

insert into tb_usuarioss(id_usuario, usuario, login, senha, 
perfil)
values(2, 'Pessoa1', 'user', 'user', 'user');

insert into tb_usuarioss(id_usuario, usuario, login, senha,
perfil)
values(1, 'administrador', 'admin', 'admin','admin');

select * from tb_usuarioss;

update tb_usuarioss set usuario = 'Mateus' where id_usuario = 2;
delete from tb_usuarioss where id_usuario = 1;

# Informações:

user:root
password:root
url: jdbc:mysql://localhost:3306/aulabanco
tipo de banco: SGDB
