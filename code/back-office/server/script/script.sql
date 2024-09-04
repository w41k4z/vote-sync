CREATE TABLE jeton_rafraichissements (
   identifiant_utilisateur VARCHAR2(50),
   jeton VARCHAR2(150) NOT NULL,
   PRIMARY KEY(identifiant_utilisateur),
   CONSTRAINT unique_jeton UNIQUE(jeton)
);

CREATE TABLE roles (
   id NUMBER,
   nom VARCHAR2(50) NOT NULL,
   niveau NUMBER NOT NULL,
   created_at DATE,
   updated_at DATE,
   PRIMARY KEY(id),
   CONSTRAINT unique_nom_role UNIQUE(nom)
);

CREATE TABLE utilisateurs (
   id NUMBER,
   id_role NUMBER NOT NULL,
   nom VARCHAR2(50) NOT NULL,
   prenom VARCHAR2(50),
   contact CHAR(10) NOT NULL,
   identifiant VARCHAR2(50) NOT NULL,
   mot_de_passe VARCHAR2(150) NOT NULL,
   created_at DATE,
   updated_at DATE,
   PRIMARY KEY(id),
   CONSTRAINT unique_contact_utilisateur UNIQUE(contact),
   CONSTRAINT unique_identifiant_utilisateur UNIQUE(identifiant),
   FOREIGN KEY(id_role) REFERENCES roles(id)
);

CREATE TABLE provinces (
   id NUMBER,
   nom VARCHAR2(12) NOT NULL,
   PRIMARY KEY(id),
   CONSTRAINT unique_nom_province UNIQUE(nom)
);


insert into roles values(1, 'OPERATOR', 0, sysdate, sysdate); 
insert into roles values(2, 'MANAGER', 5, sysdate, sysdate); 
insert into roles values(3, 'ADMIN', 10, sysdate, sysdate);

insert into utilisateurs values(default, 3, 'Admin', 'Admin', '0340000000', 'admin', '{bcrypt}$2a$10$J9f30WOeQCp2sJ8IjZwd0eUYK0xFF.U6Ou0Fw9vsGWoKdzSA7Nwje', sysdate, sysdate);

insert into provinces values(1, 'Antananarivo');
insert into provinces values(2, 'Antsiranana');
insert into provinces values(3, 'Fianarantsoa');
insert into provinces values(4, 'Mahajanga');
insert into provinces values(5, 'Toamasina');
insert into provinces values(6, 'Toliara');