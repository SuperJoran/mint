CREATE TABLE IF NOT EXISTS t_person
(
  uuid     VARCHAR(255) NOT NULL
    CONSTRAINT t_person_pkey
    PRIMARY KEY,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL
    CONSTRAINT uk_if2k9f2vlnem3o4ylvrhv8ko7
    UNIQUE
);

CREATE TABLE IF NOT EXISTS t_bankaccount
(
  uuid               VARCHAR(255) NOT NULL
    CONSTRAINT t_bankaccount_pkey
    PRIMARY KEY,
  bank               INTEGER,
  name               VARCHAR(255),
  number             VARCHAR(255) NOT NULL,
  administrator_uuid VARCHAR(255) NOT NULL
    CONSTRAINT fkdabpxtvqrv8v9st40u63icqyg
    REFERENCES t_person,
  owner_uuid         VARCHAR(255)
    CONSTRAINT fk9ip1bpx3gmh5gwgmhgp4a3kiy
    REFERENCES t_person,
  CONSTRAINT uk9eq8ii28jdnbs1mynytpkxvpp
  UNIQUE (number, administrator_uuid)
);

CREATE TABLE IF NOT EXISTS t_category
(
  uuid                VARCHAR(255) NOT NULL
    CONSTRAINT t_category_pkey
    PRIMARY KEY,
  name                VARCHAR(255),
  category_group_uuid VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS t_category_group
(
  uuid               VARCHAR(255) NOT NULL
    CONSTRAINT t_category_group_pkey
    PRIMARY KEY,
  category_type      VARCHAR(255) NOT NULL,
  name               VARCHAR(255),
  administrator_uuid VARCHAR(255) NOT NULL
    CONSTRAINT fk37gsdqpantkwnx7ter3prbd8f
    REFERENCES t_person
);

ALTER TABLE t_category
  ADD CONSTRAINT fk6vm7doxwfeuk9df42wsvq5ufo
FOREIGN KEY (category_group_uuid) REFERENCES t_category_group;

CREATE TABLE IF NOT EXISTS t_statement
(
  uuid                    VARCHAR(255) NOT NULL
    CONSTRAINT t_statement_pkey
    PRIMARY KEY,
  amount                  NUMERIC(19, 2),
  csv_line                VARCHAR(255),
  date                    DATE,
  description             VARCHAR(255),
  category_uuid           VARCHAR(255)
    CONSTRAINT fkk8vewn0icd9ks2wfco69rv5st
    REFERENCES t_category,
  DESTINATIONACCOUNT_NUMBER VARCHAR(255),
  originatingaccount_uuid VARCHAR(255)
    CONSTRAINT fkm9o4gja10hx978h8l0f9rtuuu
    REFERENCES t_bankaccount
);