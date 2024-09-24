CREATE OR REPLACE VIEW resultats_par_bv AS
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
CREATE OR REPLACE VIEW resultat_statistique_par_bv AS
SELECT
    r.id_election,
    r.id_bv,
    bv.code AS code_bv,
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
CREATE OR REPLACE VIEW resultat_statistique_par_fokontany AS
SELECT
    rsbv.id_election,
    fk.id AS id_fokontany,
    fk.code AS code_fokontany,
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
    fk.code
;


CREATE OR REPLACE VIEW resultats_par_commune AS
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
CREATE OR REPLACE VIEW resultat_statistique_par_commune AS
SELECT
    rsf.id_election,
    cm.id AS id_commune,
    cm.code AS code_commune,
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
    cm.code
;

-- Presidential election and legislative election only
CREATE OR REPLACE VIEW resultats_par_district AS
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
CREATE OR REPLACE VIEW resultat_statistique_par_district AS
SELECT
    rsc.id_election,
    d.id AS id_district,
    d.code AS code_district,
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
    d.code
;


-- Presidential election only
CREATE OR REPLACE VIEW resultats_par_region AS
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
CREATE OR REPLACE VIEW resultat_statistique_par_region AS
SELECT
    rsd.id_election,
    r.id AS id_region,
    r.code AS code_region,
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
    r.code
;

-- Presidential election only
CREATE OR REPLACE VIEW resultats_par_province AS
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
CREATE OR REPLACE VIEW resultat_statistique_par_province AS
SELECT
    rsr.id_election,
    p.id AS id_province,
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
    p.id
;


-- Presidential election only
CREATE OR REPLACE VIEW resultats_election AS
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
CREATE OR REPLACE VIEW resultat_statistique_election AS
SELECT
    rsp.id_election,
    SUM(rsp.inscrits) AS inscrits,
    SUM(rsp.blancs) AS blancs,
    SUM(rsp.nuls) AS nuls,
    SUM(rsp.exprimes) AS exprimes
FROM resultat_statistique_par_province rsp
GROUP BY
    rsp.id_election
;