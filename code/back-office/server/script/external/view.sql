CREATE OR REPLACE VIEW candidats_presidentiels AS
SELECT
    ec.id AS id_enregistrement,
    ec.id_election,
    ec.id_candidat,
    c.id_entite_politique,
    c.information AS information_candidat,
    ec.numero_candidat,
    ep.nom AS entite_politique,
    ep.description AS description_entite_politique,
    ec.chemin_photo,
    ec.date_enregistrement
FROM enregistrement_candidats ec
JOIN candidats c
    ON ec.id_candidat = c.id
JOIN entites_politiques ep
    ON c.id_entite_politique = ep.id
;

CREATE OR REPLACE VIEW candidats_legislatifs AS
SELECT
    ec.id AS id_enregistrement,
    ec.id_election,
    ec.id_candidat,
    d.id AS id_district,
    c.id_entite_politique,
    c.information AS information_candidat,
    ec.numero_candidat,
    ep.nom AS entite_politique,
    ep.description AS description_entite_politique,
    ec.chemin_photo,
    ec.date_enregistrement
FROM enregistrement_candidats ec
JOIN districts d
    ON ec.id_district = d.id
JOIN candidats c
    ON ec.id_candidat = c.id
JOIN entites_politiques ep
    ON c.id_entite_politique = ep.id
;

CREATE OR REPLACE VIEW candidats_locaux AS
SELECT
    ec.id AS id_enregistrement,
    ec.id_election,
    ec.id_candidat,
    mc.id AS id_municipalite,
    c.id_entite_politique,
    c.information AS information_candidat,
    ec.numero_candidat,
    ep.nom AS entite_politique,
    ep.description AS description_entite_politique,
    ec.chemin_photo,
    ec.date_enregistrement
FROM enregistrement_candidats ec
JOIN municipalites mc
    ON ec.id_municipalite = mc.id
JOIN candidats c
    ON ec.id_candidat = c.id
JOIN entites_politiques ep
    ON c.id_entite_politique = ep.id
GROUP BY
    ec.id,
    ec.id_election,
    ec.id_candidat,
    mc.id,
    c.id_entite_politique,
    c.information,
    ec.numero_candidat,
    ep.nom,
    ep.description,
    ec.chemin_photo,
    ec.date_enregistrement
;

CREATE OR REPLACE VIEW electeurs_enregistres AS
SELECT
    e.*,
    ee.id_election,
    ee.id_bv
FROM enregistrement_electeurs ee
JOIN electeurs e
    ON ee.id_electeur = e.id
WHERE ee.vote < 10
;