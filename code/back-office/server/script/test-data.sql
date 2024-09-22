-- Election locale 05 Novembre 2024
insert into elections (id, id_type_election, nom, date_debut, etat)
values (DEFAULT, 3, 'Election locale 05 Novembre 2024', TO_DATE('2024-11-05', 'YYYY-MM-DD'), 0);

-- Entité politique
INSERT INTO entites_politiques (nom, description)
VALUES ('TNF', 'Tiako Ny Fireneko');
INSERT INTO entites_politiques (nom, description)
VALUES ('MM', 'Miara Mandroso');

-- Candidats
INSERT INTO candidats (id_entite_politique, information)
VALUES (1, 'Rasoa Mamy');
INSERT INTO candidats (id_entite_politique, information)
VALUES (1, 'Rakoto Andry');
INSERT INTO candidats (id_entite_politique, information)
VALUES (2, 'Rasoanirina Hasina');
INSERT INTO candidats (id_entite_politique, information)
VALUES (2, 'Randriamiharisoa Lova');

-- Enregistrement candidats commune 4e Arrondissement
INSERT INTO enregistrement_candidats (id_election, id_candidat, id_commune, numero_candidat, chemin_photo, date_enregistrement)
VALUES (1, 1, 1, 1, 'No image.jpg', SYSDATE);
INSERT INTO enregistrement_candidats (id_election, id_candidat, id_commune, numero_candidat, chemin_photo, date_enregistrement)
VALUES (1, 3, 1, 2, 'No image.jpg', SYSDATE);

-- Enregistrement candidats commune 5e Arrondissement
INSERT INTO enregistrement_candidats (id_election, id_candidat, id_commune, numero_candidat, chemin_photo, date_enregistrement)
VALUES (1, 2, 2, 1, 'No image.jpg', SYSDATE);
INSERT INTO enregistrement_candidats (id_election, id_candidat, id_commune, numero_candidat, chemin_photo, date_enregistrement)
VALUES (1, 4, 2, 2, 'No image.jpg', SYSDATE);

-- Chef CID 4e Arrondissement et V
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 4, 'Ndahimananjara', 'Jaona', '0325506338', '4e Arrondissement', '{noop}4e Arrondissement');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 4, 'Harifanja', 'Tombovelo', '0349857376', '5e Arrondissement', '{noop}5e Arrondissement');

-- Responsable de bureau de vote
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rasoa', 'Manitra', '0349101010', '111001030101', '{noop}password1');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rakoto', 'Feno', '0349202020', '111001030102', '{noop}password2');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rasoanaivo', 'Lova', '0349303030', '111001030103', '{noop}password3');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Randriamalala', 'Tiana', '0349404040', '111001030104', '{noop}password4');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rabetokotany', 'Voahangy', '0349505050', '111001030105', '{noop}password5');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rasoarimalala', 'Ando', '0349606060', '111001040101', '{noop}password6');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Ramaroson', 'Dina', '0349707070', '111001040102', '{noop}password7');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Randrianarisoa', 'Zara', '0349808080', '111001040103', '{noop}password8');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Ravelo', 'Aina', '0349909090', '111001040104', '{noop}password9');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andriamanjato', 'Faly', '0349111011', '111001040105', '{noop}password10');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Razafimahaleo', 'Sarobidy', '0349222022', '111001040106', '{noop}password11');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Ralaivao', 'Herizo', '0349333033', '111001040107', '{noop}password12');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andrianarisoa', 'Tovo', '0349444044', '111101060101', '{noop}password13');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rafidison', 'Malala', '0349555055', '111101060102', '{noop}password14');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rakotomavo', 'Njara', '0349666066', '111101060103', '{noop}password15');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andriamalala', 'Miora', '0349777077', '111101060104', '{noop}password16');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rajaonarivelo', 'Tahina', '0349888088', '111101060105', '{noop}password17');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andriamanampy', 'Rado', '0349999099', '111101060106', '{noop}password18');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Razakanirina', 'Tsanta', '0349111122', '111101200101', '{noop}password19');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Randrianarivelo', 'Hery', '0349222233', '111101200102', '{noop}password20');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rakotondramanana', 'Lova', '0349333344', '111101200103', '{noop}password21');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rasoanarivo', 'Zina', '0349444455', '111101200104', '{noop}password22');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rabarison', 'Solohery', '0349555566', '111101200105', '{noop}password23');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andrianjafy', 'Faniry', '0349666677', '111101200106', '{noop}password24');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Raveloson', 'Zo', '0349777788', '111101200107', '{noop}password25');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Raharimalala', 'Fetra', '0349888899', '111101200108', '{noop}password26');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andrianantenaina', 'Tantely', '0349999900', '111101200109', '{noop}password27');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Randrianasolo', 'Kanto', '0349111133', '111101200110', '{noop}password28');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Rafeno', 'Santatra', '0349222244', '111101200111', '{noop}password29');
insert into utilisateurs(id, id_role, nom, prenom, contact, identifiant, mot_de_passe)
values(DEFAULT, 2, 'Andrianirina', 'Lanja', '0349333355', '111101200112', '{noop}password30');

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