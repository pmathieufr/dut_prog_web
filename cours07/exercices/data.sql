create table users(
  login varchar(10),
  mdp text,
  nom text,
  prenom text,
  role text,
  adresse text,
  primary key(login)
);

insert into users values ('paul','paul','paul','paul','admin',null);
insert into users values ('jean','jean','jean','jean','user',null);

