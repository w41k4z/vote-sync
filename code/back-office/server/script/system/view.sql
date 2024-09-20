CREATE VIEW stat_utilisateur AS
SELECT
    id_role,
    nom_role,
    SUM(nombre_utilisateur) AS nombre_utilisateur
FROM (
    SELECT
        u.id_role,
        r.nom AS nom_role,
        COUNT(u.id) AS nombre_utilisateur
    FROM utilisateurs u
    JOIN roles r ON u.id_role = r.id
    GROUP BY u.id_role, r.nom
    UNION
    SELECT
        r2.id,
        r2.nom,
        0
    FROM roles r2
)
GROUP BY id_role, nom_role
;

CREATE VIEW election_en_cours AS
SELECT
    e.id,
    e.id_type_election,
    te.nom AS type_election,
    e.nom,
    e.date_debut,
    e.date_fin
FROM election e
JOIN type_election te 
    ON e.id_type_election = te.id
WHERE 
    e.date_debut <= CURRENT_DATE AND
    e.date_fin IS NULL
AND ROWNUM = 1
;

CREATE VIEW resultats_par_bv AS
SELECT
    r.id_election,
    r.id_bv,
    bv.code AS code_bv,
    ec.numero_candidat,
    c.id AS id_candidat,
    c.information AS information_candidat,
    dr.voix AS voix_candidat,
    ec.chemin_photo AS chemin_photo_candidat
FROM details_resultats dr
JOIN resultats r
    ON dr.id_resultat = r.id
JOIN bv
    ON r.id_bv = bv.id
JOIN enregistrement_candidats ec
    ON dr.id_enregistrement_candidat = ec.id
JOIN candidats c
    ON ec.id_candidat = c.id
WHERE r.etat = 10
;

CREATE VIEW resultats_par_fokontany AS
SELECT
    rbv.id_election,
    fk.id AS id_fokontany,
    fk.code AS code_fokontany,
    rbv.numero_candidat,
    rbv.id_candidat,
    rbv.information_candidat,
    SUM(rbv.voix_candidat) AS voix_candidat,
    rbv.chemin_photo_candidat
FROM resultats_par_bv rbv
JOIN bv
    ON rbv.id_bv = bv.id
JOIN cv
    ON bv.id_cv = cv.id
JOIN fokontany fk
    ON fk.id = cv.id_fokontany
GROUP BY
    rbv.id_election,
    fk.id,
    fk.code,
    rbv.numero_candidat,
    rbv.id_candidat,
    rbv.information_candidat,
    rbv.chemin_photo_candidat
;

CREATE VIEW resultats_par_commune AS
SELECT
    rfk.id_election,
    cm.id AS id_commune,
    cm.code AS code_commune,
    rfk.numero_candidat,
    rfk.id_candidat,
    rfk.information_candidat,
    SUM(rfk.voix_candidat) AS voix_candidat,
    rfk.chemin_photo_candidat
FROM resultats_par_fokontany rfk
JOIN fokontany fk
    ON rfk.id_fokontany = fk.id
JOIN communes cm
    ON fk.id_commune = cm.id
GROUP BY
    rfk.id_election,
    cm.id,
    cm.code,
    rfk.numero_candidat,
    rfk.id_candidat,
    rfk.information_candidat,
    rfk.chemin_photo_candidat
;

-- Presidential election and legislative election only
CREATE VIEW resultats_par_district AS
SELECT
    rcm.id_election,
    d.id AS id_district,
    d.code AS code_district,
    rcm.numero_candidat,
    rcm.id_candidat,
    rcm.information_candidat,
    SUM(rcm.voix_candidat) AS voix_candidat,
    rcm.chemin_photo_candidat
FROM resultats_par_commune rcm
JOIN communes cm
    ON rcm.id_commune = cm.id
JOIN districts d
    ON cm.id_district = d.id
GROUP BY
    rcm.id_election,
    d.id,
    d.code,
    rcm.numero_candidat,
    rcm.id_candidat,
    rcm.information_candidat,
    rcm.chemin_photo_candidat
;

-- Presidential election only
CREATE VIEW resultats_par_region AS
SELECT
    rds.id_election,
    r.id AS id_region,
    r.code AS code_region,
    rds.numero_candidat,
    rds.id_candidat,
    rds.information_candidat,
    SUM(rds.voix_candidat) AS voix_candidat,
    rds.chemin_photo_candidat
FROM resultats_par_district rds
JOIN districts d
    ON rds.id_district = d.id
JOIN regions r
    ON d.id_region = r.id
GROUP BY
    rds.id_election,
    r.id,
    r.code,
    rds.numero_candidat,
    rds.id_candidat,
    rds.information_candidat,
    rds.chemin_photo_candidat
;

-- Presidential election only
CREATE VIEW resultats_par_province AS
SELECT
    rrg.id_election,
    p.id AS id_province,
    rrg.numero_candidat,
    rrg.id_candidat,
    rrg.information_candidat,
    SUM(rrg.voix_candidat) AS voix_candidat,
    rrg.chemin_photo_candidat
FROM resultats_par_region rrg
JOIN regions r
    ON rrg.id_region = r.id
JOIN provinces p
    ON r.id_province = p.id
GROUP BY
    rrg.id_election,
    p.id,
    rrg.numero_candidat,
    rrg.id_candidat,
    rrg.information_candidat,
    rrg.chemin_photo_candidat
;

-- Presidential election only
CREATE VIEW resultats_election AS
SELECT
    rpp.id_election,
    rpp.numero_candidat,
    rpp.id_candidat,
    rpp.information_candidat,
    SUM(rpp.voix_candidat) AS voix_candidat,
    rpp.chemin_photo_candidat
FROM resultats_par_province rpp
GROUP BY
    rpp.id_election,
    rpp.numero_candidat,
    rpp.id_candidat,
    rpp.information_candidat,
    rpp.chemin_photo_candidat
;