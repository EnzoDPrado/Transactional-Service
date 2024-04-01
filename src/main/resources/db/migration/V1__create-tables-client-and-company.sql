create table client(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    cpf varchar(11) not null unique,
    primary key(id)
);

create table company(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    cnpj varchar(14) not null unique,
    primary key(id)
);