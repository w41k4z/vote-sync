insert into type_elections values(1, 'Presidentielle');
insert into type_elections values(2, 'Legislative');
insert into type_elections values(3, 'Locale');

insert into roles values(1, 'OPERATOR', 0); 
insert into roles values(2, 'MANAGER', 5); 
insert into roles values(3, 'ADMIN', 10);
insert into roles values(4, 'CCID', 5);

insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 3, 'Jean', 'Robert', '0349315928', 'admin', '{bcrypt}$2a$10$J9f30WOeQCp2sJ8IjZwd0eUYK0xFF.U6Ou0Fw9vsGWoKdzSA7Nwje');

insert into type_alertes
values(1, 'Enregistrement suspect', 0);
insert into type_alertes
values(2, 'Données non synchronisées', 5);
insert into type_alertes
values(3, 'Incohérence de données', 10);

insert into provinces values('1', 'Antananarivo');
insert into provinces values('2', 'Antsiranana');
insert into provinces values('3', 'Fianarantsoa');
insert into provinces values('4', 'Mahajanga');
insert into provinces values('5', 'Toamasina');
insert into provinces values('6', 'Toliara');
-- Run all the python script for the spatial data initialization
-- Then call the import_data procedure