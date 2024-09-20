CREATE VIEW candidats_presidentiels AS
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

CREATE VIEW candidats_legislatifs AS
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

CREATE VIEW candidats_locaux AS
SELECT
    ec.id AS id_enregistrement,
    ec.id_election,
    ec.id_candidat,
    cm.id AS id_commune,
    c.id_entite_politique,
    c.information AS information_candidat,
    ec.numero_candidat,
    ep.nom AS entite_politique,
    ep.description AS description_entite_politique,
    ec.chemin_photo,
    ec.date_enregistrement
FROM enregistrement_candidats ec
JOIN communes cm
    ON ec.id_district = cm.id
JOIN candidats c
    ON ec.id_candidat = c.id
JOIN entites_politiques ep
    ON c.id_entite_politique = ep.id
;