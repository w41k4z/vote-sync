-- Entité politique
INSERT INTO entites_politiques (id, nom, description)
VALUES (1, 'TNF', 'Tiako Ny Fireneko');
INSERT INTO entites_politiques (id, nom, description)
VALUES (2, 'MM', 'Miara Mandroso');
INSERT INTO entites_politiques (id, nom, description)
VALUES (3, 'IRMM', 'Isika Rehetra Miara Mandroso');
INSERT INTO entites_politiques (id, nom, description)
VALUES (4, 'AM', 'Alefa Madagasikara');

-- Candidats
INSERT INTO candidats (id, id_entite_politique, information)
VALUES (1, 1, 'Rasoa Mamy');
INSERT INTO candidats (id, id_entite_politique, information)
VALUES (2, 2, 'Rakoto Andry');
INSERT INTO candidats (id, id_entite_politique, information)
VALUES (3, 3, 'Rasoanirina Hasina');
INSERT INTO candidats (id, id_entite_politique, information)
VALUES (4, 4, 'Randriamiharisoa Lova');
INSERT INTO candidats (id, id_entite_politique, information)
VALUES (5, 1, 'Fitahiana Razanandrasoa');
INSERT INTO candidats (id, id_entite_politique, information)
VALUES (6, 3, 'Nirina Tsimisaraka');

CREATE OR REPLACE PROCEDURE data_simulation (
    election_id NUMBER
) AS
BEGIN
    -- Enregistrement candidats maire Antananarivo Renivohitra
    INSERT INTO enregistrement_candidats (id_election, id_candidat, id_municipalite, numero_candidat, chemin_photo, date_enregistrement)
    VALUES (election_id, 1, 41, 1, 'ep/TNF.png', SYSDATE);
    INSERT INTO enregistrement_candidats (id_election, id_candidat, id_municipalite, numero_candidat, chemin_photo, date_enregistrement)
    VALUES (election_id, 2, 41, 2, 'ep/MM.png', SYSDATE);
    INSERT INTO enregistrement_candidats (id_election, id_candidat, id_municipalite, numero_candidat, chemin_photo, date_enregistrement)
    VALUES (election_id, 3, 41, 3, 'ep/IRMM.png', SYSDATE);
    INSERT INTO enregistrement_candidats (id_election, id_candidat, id_municipalite, numero_candidat, chemin_photo, date_enregistrement)
    VALUES (election_id, 4, 41, 4, 'ep/AM.png', SYSDATE);

    -- Enregistrement candidats maire Antananarivo Atsimondrano
    INSERT INTO enregistrement_candidats (id_election, id_candidat, id_municipalite, numero_candidat, chemin_photo, date_enregistrement)
    VALUES (election_id, 5, 1, 1, 'ep/TNF.png', SYSDATE);
    INSERT INTO enregistrement_candidats (id_election, id_candidat, id_municipalite, numero_candidat, chemin_photo, date_enregistrement)
    VALUES (election_id, 6, 1, 2, 'ep/IRMM.png', SYSDATE);

    -- Enregistrement electeurs Atsimondrano EPP Volotara Salle 1
    INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
    VALUES (election_id, 41, 1, SYSDATE, 0);
    INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
    VALUES (election_id, 41, 2, SYSDATE, 0);
    INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
    VALUES (election_id, 41, 3, SYSDATE, 0);
    INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
    VALUES (election_id, 41, 4, SYSDATE, 0);
    INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
    VALUES (election_id, 41, 5, SYSDATE, 0);
    INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
    VALUES (election_id, 41, 6, SYSDATE, 0);
    INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
    VALUES (election_id, 41, 7, SYSDATE, 0);
    INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
    VALUES (election_id, 41, 8, SYSDATE, 0);
    INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
    VALUES (election_id, 41, 9, SYSDATE, 0);
    INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
    VALUES (election_id, 41, 10, SYSDATE, 0);
 
    -- QR Code
    INSERT INTO code_qr(contenu, date_creation, date_expiration)
    VALUES('110511060101', TO_DATE('2024-11-28', 'YYYY-MM-DD'), TO_DATE('2024-11-30', 'YYYY-MM-DD'));
END;
/


-- Chef CID 4e Arrondissement et V
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 4, 'Ndahimananjara', 'Jaona', '0325506338', '4e Arrondissement', '{noop}4e Arrondissement');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 4, 'Harifanja', 'Tombovelo', '0349857376', '5e Arrondissement', '{noop}5e Arrondissement');

-- Responsable de bureau de vote
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rasoa', 'Manitra', '0349101010', '111001030101', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rakoto', 'Feno', '0349202020', '111001030102', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rasoanaivo', 'Lova', '0349303030', '111001030103', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Randriamalala', 'Tiana', '0349404040', '111001030104', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rabetokotany', 'Voahangy', '0349505050', '111001030105', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rasoarimalala', 'Ando', '0349606060', '111001040101', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Ramaroson', 'Dina', '0349707070', '111001040102', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Randrianarisoa', 'Zara', '0349808080', '111001040103', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Ravelo', 'Aina', '0349909090', '111001040104', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andriamanjato', 'Faly', '0349111011', '111001040105', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Razafimahaleo', 'Sarobidy', '0349222022', '111001040106', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Ralaivao', 'Herizo', '0349333033', '111001040107', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andrianarisoa', 'Tovo', '0349444044', '111101060101', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rafidison', 'Malala', '0349555055', '111101060102', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rakotomavo', 'Njara', '0349666066', '111101060103', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andriamalala', 'Miora', '0349777077', '111101060104', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rajaonarivelo', 'Tahina', '0349888088', '111101060105', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andriamanampy', 'Rado', '0349999099', '111101060106', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Razakanirina', 'Tsanta', '0349111122', '111101200101', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Randrianarivelo', 'Hery', '0349222233', '111101200102', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rakotondramanana', 'Lova', '0349333344', '111101200103', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rasoanarivo', 'Zina', '0349444455', '111101200104', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rabarison', 'Solohery', '0349555566', '111101200105', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andrianjafy', 'Faniry', '0349666677', '111101200106', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Raveloson', 'Zo', '0349777788', '111101200107', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Raharimalala', 'Fetra', '0349888899', '111101200108', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andrianantenaina', 'Tantely', '0349999900', '111101200109', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Randrianasolo', 'Kanto', '0349111133', '111101200110', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rafeno', 'Santatra', '0349222244', '111101200111', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andrianirina', 'Lanja', '0349333355', '111101200112', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Randriamanana', 'Flerys', '0349555555', '110511060101', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rabesalama', 'Flavis', '0349666666', '110511060102', '{noop}bv');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Harimeva', 'Judette', '0349777777', '110511060103', '{noop}bv');

-- electeurs
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('123456789012', 'Rasoa', 'Mamy', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('234567890123', 'Rakoto', 'Andry', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('345678901234', 'Rasoanirina', 'Hasina', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('456789012345', 'Randriamiharisoa', 'Lova', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('567890123456', 'Raharinosy', 'Tiana', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('678901234567', 'Rabarisoa', 'Malala', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('789012345678', 'Ratsimbazafy', 'Feno', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('890123456789', 'Rafidison', 'Santatra', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('901234567890', 'Andriamalala', 'Zo', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('012345678901', 'Rajaonarivelo', 'Hery', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('123456789013', 'Rakotomalala', 'Fanja', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('234567890124', 'Rasamimanana', 'Miora', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('345678901235', 'Rasoanivo', 'Dina', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('456789012346', 'Randrianasolo', 'Tahina', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('567890123457', 'Ravelo', 'Voahangy', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('678901234568', 'Andrianantenaina', 'Aina', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('789012345679', 'Rambeloson', 'Kanto', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('890123456790', 'Andrianirina', 'Faniry', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('901234567891', 'Rasolo', 'Sarobidy', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('012345678902', 'Rakotondrasoa', 'Njara', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('123456789014', 'Andriamiarisoa', 'Hasina', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('234567890125', 'Razafindrakoto', 'Tovo', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('345678901236', 'Ratsirahonana', 'Manitra', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('456789012347', 'Ramaroson', 'Solonirina', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('567890123458', 'Ravonirina', 'Lanto', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('678901234569', 'Rasamindrakoto', 'Felana', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('789012345680', 'Randriamanalina', 'Tantely', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('890123456791', 'Ravelojaona', 'Tovo', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('901234567892', 'Rasoamampianina', 'Vola', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('012345678903', 'Randriamanantsoa', 'Fetra', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('112233445566', 'Andriamampianina', 'Harisoa', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('223344556677', 'Ravelojaona', 'Faniry', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('334455667788', 'Rabeharisoa', 'Lalaina', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('445566778899', 'Rakotovao', 'Voahirana', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('556677889900', 'Randrianarisoa', 'Faly', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('667788990011', 'Rafalimanana', 'Vero', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('778899001122', 'Rasamison', 'Sitraka', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('889900112233', 'Rasolonjatovo', 'Hanta', 1);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('990011223344', 'Andrianjatovo', 'Tojo', 0);
INSERT INTO electeurs (cin, nom, prenom, genre)
VALUES ('001122334455', 'Randrianambinina', 'Zo', 1);

-- Opérateur
INSERT INTO utilisateurs (id_role, nom, prenom, contact, identifiant, mot_de_passe)
VALUES (1, 'Ratsimba', 'Harisoa', '0349333360', '0349333360', '{noop}0349333355');
INSERT INTO utilisateurs (id_role, nom, prenom, contact, identifiant, mot_de_passe)
VALUES (1, 'Randriamahefa', 'Tiana', '0349333356', '0349333356', '{noop}0349333356');
INSERT INTO utilisateurs (id_role, nom, prenom, contact, identifiant, mot_de_passe)
VALUES (1, 'Rafidy', 'Mbolatiana', '0349333357', '0349333357', '{noop}0349333357');
INSERT INTO utilisateurs (id_role, nom, prenom, contact, identifiant, mot_de_passe)
VALUES (1, 'Rakotovao', 'Ando', '0349333358', '0349333358', '{noop}0349333358');
INSERT INTO utilisateurs (id_role, nom, prenom, contact, identifiant, mot_de_passe)
VALUES (1, 'Ramaroson', 'Fenosoa', '0349333359', '0349333359', '{noop}0349333359');


-- Enregistrement electeurs 4e Arrondissement Ampangabe Anjanakinifolo EPP Anosipatrana Salle 2
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 2, 1, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 2, 2, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 2, 3, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 2, 4, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 2, 5, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 2, 6, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 2, 7, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 2, 8, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 2, 9, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 2, 10, SYSDATE, 0);


-- Enregistrement electeurs 4e Arrondissement Andavamamba Ambilanibe EPP Ilanivato Salle1
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 6, 11, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 6, 12, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 6, 13, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 6, 14, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 6, 15, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 6, 16, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 6, 17, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 6, 18, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 6, 19, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 6, 20, SYSDATE, 0);


-- Enregistrement electeurs 5e Arrondissement Ivandry EPP Ivandry S3
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 15, 21, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 15, 22, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 15, 23, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 15, 24, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 15, 25, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 15, 26, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 15, 27, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 15, 28, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 15, 29, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 15, 30, SYSDATE, 0);


-- Enregistrement electeurs 5e Arrondissement Ivandry LTP S10
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 28, 31, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 28, 32, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 28, 33, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 28, 34, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 28, 35, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 28, 36, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 28, 37, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 28, 38, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 28, 39, SYSDATE, 0);
INSERT INTO enregistrement_electeurs (id_election, id_bv, id_electeur, date_enregistrement, vote)
VALUES (1, 28, 40, SYSDATE, 0);


-- Code qr
INSERT INTO code_qr(contenu, date_creation, date_expiration) 
VALUES('ELECT-1-111001030102', TO_DATE('2024-11-15', 'YYYY-MM-DD'), TO_DATE('2024-11-16', 'YYYY-MM-DD'));
INSERT INTO code_qr(contenu, date_creation, date_expiration) 
VALUES('ELECT-1-111001040101', TO_DATE('2024-11-15', 'YYYY-MM-DD'), TO_DATE('2024-11-16', 'YYYY-MM-DD'));
INSERT INTO code_qr(contenu, date_creation, date_expiration) 
VALUES('ELECT-1-111101060103', TO_DATE('2024-11-15', 'YYYY-MM-DD'), TO_DATE('2024-11-16', 'YYYY-MM-DD'));
INSERT INTO code_qr(contenu, date_creation, date_expiration) 
VALUES('ELECT-1-111101200110', TO_DATE('2024-11-15', 'YYYY-MM-DD'), TO_DATE('2024-11-16', 'YYYY-MM-DD'));