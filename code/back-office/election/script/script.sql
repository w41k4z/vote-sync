CREATE TABLE roles (
   id NUMBER,
   nom VARCHAR2(50) NOT NULL,
   niveau NUMBER NOT NULL,
   created_at DATE,
   updated_at DATE,
   PRIMARY KEY(id),
   CONSTRAINT unique_nom UNIQUE(nom)
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
   CONSTRAINT unique_contact UNIQUE(contact),
   CONSTRAINT unique_identifiant UNIQUE(identifiant),
   FOREIGN KEY(id_role) REFERENCES roles(id)
);


insert into roles values(default, 'OPERATOR', 0, sysdate, sysdate); 
insert into roles values(default, 'MANAGER', 5, sysdate, sysdate); 
insert into roles values(default, 'ADMIN', 10, sysdate, sysdate);