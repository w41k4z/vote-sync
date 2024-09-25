CREATE OR REPLACE VIEW resultats_par_bv AS
SELECT
    ROW_NUMBER() OVER(ORDER BY r.id_election) AS id,
    r.id_election,
    r.id_bv,
    bv.code AS code_bv,
    bv.nom AS nom_bv,
    c.id AS id_candidat,
    ec.numero_candidat,
    c.information AS information_candidat,
    ep.id AS id_entite_politique,
    ep.nom AS nom_entite_politique,
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
JOIN entites_politiques ep
    ON c.id_entite_politique = ep.id
WHERE r.etat = 10
;
CREATE OR REPLACE VIEW resultat_statistique_par_bv AS
SELECT
    ROW_NUMBER() OVER(ORDER BY r.id_election) AS id,
    r.id_election,
    r.id_bv,
    bv.code AS code_bv,
    bv.nom AS nom_bv,
    r.inscrits,
    r.blancs,
    r.nuls,
    dr.exprimes
FROM resultats r
JOIN (
    SELECT
        id_resultat,
        SUM(voix) AS exprimes
    FROM details_resultats
    GROUP BY id_resultat
) dr
    ON r.id = dr.id_resultat
JOIN bv
    ON r.id_bv = bv.id
WHERE r.etat = 10
;


CREATE OR REPLACE VIEW resultats_par_fokontany AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rbv.id_election) AS id,
    rbv.id_election,
    fk.id AS id_fokontany,
    fk.code AS code_fokontany,
    fk.nom AS nom_fokontany,
    rbv.id_candidat,
    rbv.numero_candidat,
    rbv.information_candidat,
    rbv.id_entite_politique,
    rbv.nom_entite_politique,
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
    fk.nom,
    rbv.numero_candidat,
    rbv.id_candidat,
    rbv.information_candidat,
    rbv.id_entite_politique,
    rbv.nom_entite_politique,
    rbv.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_fokontany AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsbv.id_election) AS id,
    rsbv.id_election,
    fk.id AS id_fokontany,
    fk.code AS code_fokontany,
    fk.nom AS nom_fokontany,
    SUM(rsbv.inscrits) AS inscrits,
    SUM(rsbv.blancs) AS blancs,
    SUM(rsbv.nuls) AS nuls,
    SUM(rsbv.exprimes) AS exprimes
FROM resultat_statistique_par_bv rsbv
JOIN bv
    ON rsbv.id_bv = bv.id
JOIN cv
    ON bv.id_cv = cv.id
JOIN fokontany fk
    ON fk.id = cv.id_fokontany
GROUP BY
    rsbv.id_election,
    fk.id,
    fk.code,
    fk.nom
;


CREATE OR REPLACE VIEW resultats_par_commune AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rfk.id_election) AS id,
    rfk.id_election,
    cm.id AS id_commune,
    cm.code AS code_commune,
    cm.nom AS nom_commune,
    rfk.id_candidat,
    rfk.numero_candidat,
    rfk.information_candidat,
    rfk.id_entite_politique,
    rfk.nom_entite_politique,
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
    cm.nom,
    rfk.numero_candidat,
    rfk.id_candidat,
    rfk.information_candidat,
    rfk.id_entite_politique,
    rfk.nom_entite_politique,
    rfk.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_commune AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsf.id_election) AS id,
    rsf.id_election,
    cm.id AS id_commune,
    cm.code AS code_commune,
    cm.nom AS nom_commune,
    SUM(rsf.inscrits) AS inscrits,
    SUM(rsf.blancs) AS blancs,
    SUM(rsf.nuls) AS nuls,
    SUM(rsf.exprimes) AS exprimes
FROM resultat_statistique_par_fokontany rsf
JOIN fokontany fk
    ON fk.id = rsf.id_fokontany
JOIN communes cm
    ON cm.id = fk.id_commune
GROUP BY
    rsf.id_election,
    cm.id,
    cm.code,
    cm.nom
;

-- Presidential election and legislative election only
CREATE OR REPLACE VIEW resultats_par_district AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rcm.id_election) AS id,
    rcm.id_election,
    d.id AS id_district,
    d.code AS code_district,
    d.nom AS nom_district,
    rcm.id_candidat,
    rcm.numero_candidat,
    rcm.information_candidat,
    rcm.id_entite_politique,
    rcm.nom_entite_politique,
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
    d.nom,
    rcm.numero_candidat,
    rcm.id_candidat,
    rcm.information_candidat,
    rcm.id_entite_politique,
    rcm.nom_entite_politique,
    rcm.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_district AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsc.id_election) AS id,
    rsc.id_election,
    d.id AS id_district,
    d.code AS code_district,
    d.nom AS nom_district,
    SUM(rsc.inscrits) AS inscrits,
    SUM(rsc.blancs) AS blancs,
    SUM(rsc.nuls) AS nuls,
    SUM(rsc.exprimes) AS exprimes
FROM resultat_statistique_par_commune rsc
JOIN communes cm
    ON cm.id = rsc.id_commune
JOIN districts d
    ON d.id = cm.id_district
GROUP BY
    rsc.id_election,
    d.id,
    d.code,
    d.nom
;


-- Presidential election only
CREATE OR REPLACE VIEW resultats_par_region AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rds.id_election) AS id,
    rds.id_election,
    r.id AS id_region,
    r.code AS code_region,
    r.nom AS nom_region,
    rds.id_candidat,
    rds.numero_candidat,
    rds.information_candidat,
    rds.id_entite_politique,
    rds.nom_entite_politique,
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
    r.nom,
    rds.numero_candidat,
    rds.id_candidat,
    rds.information_candidat,
    rds.id_entite_politique,
    rds.nom_entite_politique,
    rds.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_region AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsd.id_election) AS id,
    rsd.id_election,
    r.id AS id_region,
    r.code AS code_region,
    r.nom AS nom_region,
    SUM(rsd.inscrits) AS inscrits,
    SUM(rsd.blancs) AS blancs,
    SUM(rsd.nuls) AS nuls,
    SUM(rsd.exprimes) AS exprimes
FROM resultat_statistique_par_district rsd
JOIN districts d
    ON d.id = rsd.id_district
JOIN regions r
    ON r.id = d.id_region
GROUP BY
    rsd.id_election,
    r.id,
    r.code,
    r.nom
;

-- Presidential election only
CREATE OR REPLACE VIEW resultats_par_province AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rrg.id_election) AS id,
    rrg.id_election,
    p.id AS id_province,
    TO_CHAR(p.id) AS code_province,
    P.nom AS nom_province,
    rrg.id_candidat,
    rrg.numero_candidat,
    rrg.information_candidat,
    rrg.id_entite_politique,
    rrg.nom_entite_politique,
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
    p.nom,
    rrg.numero_candidat,
    rrg.id_candidat,
    rrg.information_candidat,
    rrg.id_entite_politique,
    rrg.nom_entite_politique,
    rrg.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_province AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsr.id_election) AS id,
    rsr.id_election,
    p.id AS id_province,
    TO_CHAR(p.id) AS code_province,
    p.nom AS nom_province,
    SUM(rsr.inscrits) AS inscrits,
    SUM(rsr.blancs) AS blancs,
    SUM(rsr.nuls) AS nuls,
    SUM(rsr.exprimes) AS exprimes
FROM resultat_statistique_par_region rsr
JOIN regions r
    ON r.id = rsr.id_region
JOIN provinces p
    ON p.id = r.id_province
GROUP BY
    rsr.id_election,
    p.id,
    p.nom
;


-- Presidential election only
CREATE OR REPLACE VIEW resultats_election AS
SELECT
    rpp.id_election AS id,
    rpp.id_election,
    '0' AS id_pays,
    'Madagascar' AS pays,
    '0' AS code,
    rpp.id_candidat,
    rpp.numero_candidat,
    rpp.information_candidat,
    rpp.id_entite_politique,
    rpp.nom_entite_politique,
    SUM(rpp.voix_candidat) AS voix_candidat,
    rpp.chemin_photo_candidat
FROM resultats_par_province rpp
GROUP BY
    rpp.id_election,
    rpp.numero_candidat,
    rpp.id_candidat,
    rpp.information_candidat,
    rpp.id_entite_politique,
    rpp.nom_entite_politique,
    rpp.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_election AS
SELECT
    rsp.id_election AS id,
    rsp.id_election,
    '0' AS id_pays,
    'Madagascar' AS pays,
    '0' AS code,
    SUM(rsp.inscrits) AS inscrits,
    SUM(rsp.blancs) AS blancs,
    SUM(rsp.nuls) AS nuls,
    SUM(rsp.exprimes) AS exprimes
FROM resultat_statistique_par_province rsp
GROUP BY
    rsp.id_election
;