create table if not exists t_person
(
  uuid varchar(255) not null
    constraint t_person_pkey
    primary key,
  password varchar(255) not null,
  username varchar(255) not null
    constraint uk_if2k9f2vlnem3o4ylvrhv8ko7
    unique
)
;

create table if not exists t_bankaccount
(
  uuid varchar(255) not null
constraint t_bankaccount_pkey
primary key,
  bank integer,
name varchar(255),
number varchar(255) not null,
  administrator_uuid varchar(255) not null
constraint fkdabpxtvqrv8v9st40u63icqyg
references t_person,
  owner_uuid varchar(255)
constraint fk9ip1bpx3gmh5gwgmhgp4a3kiy
references t_person,
constraint uk9eq8ii28jdnbs1mynytpkxvpp
unique (number, administrator_uuid)
)
;

create table if not exists t_category
(
  uuid varchar(255) not null
constraint t_category_pkey
primary key,
name varchar(255),
  category_group_uuid varchar(255) not null
)
;

create table if not exists t_category_group
(
  uuid varchar(255) not null
constraint t_category_group_pkey
primary key,
  category_type varchar(255) not null,
name varchar(255),
  administrator_uuid varchar(255) not null
constraint fk37gsdqpantkwnx7ter3prbd8f
references t_person
)
;

alter table t_category
  add constraint fk6vm7doxwfeuk9df42wsvq5ufo
foreign key (category_group_uuid) references t_category_group
;

create table if not exists t_statement
(
  uuid varchar(255) not null
constraint t_statement_pkey
primary key,
  amount numeric(19,2),
  csv_line varchar(255),
date date,
  description varchar(255),
  category_uuid varchar(255)
constraint fkk8vewn0icd9ks2wfco69rv5st
references t_category,
  destinationaccount_uuid varchar(255)
constraint fk722wa21iwlg3dlghq5snfpwj5
references t_bankaccount,
  originatingaccount_uuid varchar(255)
constraint fkm9o4gja10hx978h8l0f9rtuuu
references t_bankaccount
)
;