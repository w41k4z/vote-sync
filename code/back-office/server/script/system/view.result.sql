CREATE OR REPLACE VIEW stat_electeur_par_bv AS
SELECT
    ROW_NUMBER() OVER(ORDER BY ee.id_election) AS num_ligne,
    ee.id_election,
    ee.id_bv,
    COUNT(ee.id_electeur) AS inscrits,
    SUM(homme_moins_36) AS homme_moins_36,
    SUM(femme_moins_36) AS femme_moins_36,
    SUM(homme_36_plus) AS homme_36_plus,
    SUM(homme_36_plus) AS femme_36_plus,
    SUM(handicape) AS handicapes,
    SUM(malvoyant) AS malvoyants
FROM enregistrement_electeurs ee
JOIN elections e
    ON ee.id_election = e.id
    AND e.etat < 20
JOIN v_electeurs_details ved
    ON ved.id = ee.id_electeur
WHERE ee.vote >= 20
GROUP BY
    ee.id_election,
    ee.id_bv
;

CREATE OR REPLACE VIEW alertes_stat_par_bv AS
SELECT
    a.id_election,
    a.id_bv,
    COUNT(a.titre) AS nombre_alertes
FROM alertes a
JOIN elections e
    ON a.id_election = e.id
    AND e.etat < 20
GROUP BY
    a.id_election,
    a.id_bv
;

CREATE OR REPLACE VIEW resultats_par_bv AS
SELECT
    ROW_NUMBER() OVER(ORDER BY r.id_election) AS num_ligne,
    r.id_election,
    r.id_bv,
    bv.code AS code_bv,
    bv.nom AS nom_bv,
    c.id AS id_candidat,
    ec.numero_candidat,
    c.information AS information_candidat,
    ep.id AS id_entite_politique,
    ep.nom AS nom_entite_politique,
    ep.description AS description_entite_politique,
    dr.voix AS voix_candidat,
    ec.chemin_photo AS chemin_photo_candidat
FROM details_resultats dr
JOIN resultats r
    ON dr.id_resultat = r.id
JOIN elections e
    ON r.id_election = e.id
    AND e.etat < 20
JOIN bv
    ON r.id_bv = bv.id
JOIN enregistrement_candidats ec
    ON dr.id_enregistrement_candidat = ec.id
JOIN candidats c
    ON ec.id_candidat = c.id
JOIN entites_politiques ep
    ON c.id_entite_politique = ep.id
WHERE r.etat >= 20
ORDER BY
    r.id_election,
    ec.numero_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_brute_par_bv AS
SELECT
    r.id_election,
    e.nom AS election,
    r.id_bv AS id,
    bv.code,
    bv.nom,
    lbv.id_fokontany,
    lbv.nom_fokontany,
    lbv.id_commune,
    lbv.nom_commune,
    lbv.id_district,
    lbv.nom_district,
    lbv.id_region,
    lbv.nom_region,
    r.inscrits,
    r.homme_moins_36,
    r.femme_moins_36,
    r.homme_36_plus,
    r.femme_36_plus,
    r.handicapes,
    r.malvoyants,
    r.blancs,
    r.nuls,
    (
        r.homme_moins_36 +
        r.femme_moins_36 +
        r.homme_36_plus +
        r.femme_36_plus
    ) - (r.blancs + r.nuls) AS exprimes,
    r.importe,
    0 AS nombre_alertes
FROM resultats r
JOIN elections e
    ON r.id_election = e.id
    AND e.etat < 20
JOIN bv
    ON r.id_bv = bv.id
JOIN liste_bv lbv
    ON lbv.id = bv.id
WHERE r.etat >= 20
UNION ALL
SELECT
    r.id_election,
    e.nom AS election,
    r.id_bv AS id,
    bv.code,
    bv.nom,
    lbv.id_fokontany,
    lbv.nom_fokontany,
    lbv.id_commune,
    lbv.nom_commune,
    lbv.id_district,
    lbv.nom_district,
    lbv.id_region,
    lbv.nom_region,
    r.inscrits,
    r.homme_moins_36,
    r.femme_moins_36,
    r.homme_36_plus,
    r.femme_36_plus,
    r.handicapes,
    r.malvoyants,
    r.blancs,
    r.nuls,
    (
        r.homme_moins_36 +
        r.femme_moins_36 +
        r.homme_36_plus +
        r.femme_36_plus
    ) - (r.blancs + r.nuls) AS exprimes,
    r.importe,
    astbv.nombre_alertes
FROM resultats r
JOIN elections e
    ON r.id_election = e.id
    AND e.etat < 20
JOIN bv
    ON r.id_bv = bv.id
JOIN liste_bv lbv
    ON lbv.id = bv.id
JOIN alertes_stat_par_bv astbv
    ON astbv.id_election = r.id_election
    AND astbv.id_bv = r.id_bv
WHERE r.etat >= 20
;
CREATE OR REPLACE VIEW resultat_statistique_par_bv AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsbv.id_election) AS num_ligne,
    rsbv.id_election,
    rsbv.election,
    rsbv.id,
    rsbv.code,
    rsbv.nom,
    rsbv.id_fokontany,
    rsbv.nom_fokontany,
    rsbv.id_commune,
    rsbv.nom_commune,
    mc.id AS id_municipalite, -- for local election
    mc.nom AS nom_municipalite, -- for local election
    mc.id_district AS id_district_municipal, -- for local election
    mc.nom_district AS nom_district_municipal, -- for local election
    rsbv.id_district,
    rsbv.nom_district,
    rsbv.id_region,
    rsbv.nom_region,
    rsbv.inscrits,
    rsbv.homme_moins_36,
    rsbv.femme_moins_36,
    rsbv.homme_36_plus,
    rsbv.femme_36_plus,
    rsbv.handicapes,
    rsbv.malvoyants,
    rsbv.blancs,
    rsbv.nuls,
    rsbv.exprimes,
    rsbv.importe,
    SUM(rsbv.nombre_alertes) AS nombre_alertes
FROM resultat_statistique_brute_par_bv rsbv
JOIN communes cm
    ON rsbv.id_commune = cm.id
JOIN municipalites mc
    ON cm.id_municipalite = mc.id
GROUP BY
    rsbv.id_election,
    rsbv.election,
    rsbv.id,
    rsbv.code,
    rsbv.nom,
    rsbv.id_fokontany,
    rsbv.nom_fokontany,
    rsbv.id_commune,
    rsbv.nom_commune,
    mc.id,
    mc.nom,
    mc.id_district,
    mc.nom_district,
    rsbv.id_district,
    rsbv.nom_district,
    rsbv.id_region,
    rsbv.nom_region,
    rsbv.inscrits,
    rsbv.homme_moins_36,
    rsbv.femme_moins_36,
    rsbv.homme_36_plus,
    rsbv.femme_36_plus,
    rsbv.handicapes,
    rsbv.malvoyants,
    rsbv.blancs,
    rsbv.nuls,
    rsbv.exprimes,
    rsbv.importe
;


CREATE OR REPLACE VIEW resultats_par_fokontany AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rbv.id_election) AS num_ligne,
    rbv.id_election,
    fk.id AS id_fokontany,
    fk.code AS code_fokontany,
    fk.nom AS nom_fokontany,
    rbv.id_candidat,
    rbv.numero_candidat,
    rbv.information_candidat,
    rbv.id_entite_politique,
    rbv.nom_entite_politique,
    rbv.description_entite_politique,
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
    rbv.description_entite_politique,
    rbv.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_fokontany AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsbv.id_election) AS num_ligne,
    rsbv.id_election,
    rsbv.election,
    fk.id,
    fk.code,
    fk.nom,
    fk.id_commune,
    rsbv.nom_commune,
    rsbv.id_municipalite,
    rsbv.nom_municipalite,
    rsbv.id_district_municipal,
    rsbv.nom_district_municipal,
    rsbv.id_district,
    rsbv.nom_district,
    rsbv.id_region,
    rsbv.nom_region,
    SUM(rsbv.inscrits) AS inscrits,
    SUM(rsbv.homme_moins_36) AS homme_moins_36,
    SUM(rsbv.femme_moins_36) AS femme_moins_36,
    SUM(rsbv.homme_36_plus) AS homme_36_plus,
    SUM(rsbv.femme_36_plus) AS femme_36_plus,
    SUM(rsbv.handicapes) AS handicapes,
    SUM(rsbv.malvoyants) AS malvoyants,
    SUM(rsbv.blancs) AS blancs,
    SUM(rsbv.nuls) AS nuls,
    SUM(rsbv.exprimes) AS exprimes,
    SUM(rsbv.importe) AS importes,
    COUNT(fk.id) AS nombre_bv,
    fktbvi.nombre_total_bv,
    SUM(rsbv.nombre_alertes) AS nombre_alertes
FROM resultat_statistique_par_bv rsbv
JOIN bv
    ON rsbv.id = bv.id
JOIN cv
    ON bv.id_cv = cv.id
JOIN fokontany fk
    ON fk.id = cv.id_fokontany
JOIN fokontany_total_bv_info fktbvi
    ON fktbvi.id_fokontany = fk.id
GROUP BY
    rsbv.id_election,
    rsbv.election,
    fk.id,
    fk.code,
    fk.nom,
    fk.id_commune,
    rsbv.nom_commune,
    rsbv.id_municipalite,
    rsbv.nom_municipalite,
    rsbv.id_district_municipal,
    rsbv.nom_district_municipal,
    rsbv.id_district,
    rsbv.nom_district,
    rsbv.id_region,
    rsbv.nom_region,
    fktbvi.nombre_total_bv
;


-- Presidential election and legislative election only
CREATE OR REPLACE VIEW resultats_par_commune AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rfk.id_election) AS num_ligne,
    rfk.id_election,
    cm.id AS id_commune,
    cm.code AS code_commune,
    cm.nom AS nom_commune,
    rfk.id_candidat,
    rfk.numero_candidat,
    rfk.information_candidat,
    rfk.id_entite_politique,
    rfk.nom_entite_politique,
    rfk.description_entite_politique,
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
    rfk.description_entite_politique,
    rfk.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_commune AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsf.id_election) AS num_ligne,
    rsf.id_election,
    rsf.election,
    cm.id,
    cm.code,
    cm.nom,
    cm.id_district,
    rsf.nom_district,
    rsf.id_region,
    rsf.nom_region,
    SUM(rsf.inscrits) AS inscrits,
    SUM(rsf.homme_moins_36) AS homme_moins_36,
    SUM(rsf.femme_moins_36) AS femme_moins_36,
    SUM(rsf.homme_36_plus) AS homme_36_plus,
    SUM(rsf.femme_36_plus) AS femme_36_plus,
    SUM(rsf.handicapes) AS handicapes,
    SUM(rsf.malvoyants) AS malvoyants,
    SUM(rsf.blancs) AS blancs,
    SUM(rsf.nuls) AS nuls,
    SUM(rsf.exprimes) AS exprimes,
    SUM(rsf.importes) AS importes,
    SUM(rsf.nombre_bv) AS nombre_bv,
    SUM(rsf.nombre_total_bv) AS nombre_total_bv,
    SUM(rsf.nombre_alertes) AS nombre_alertes
FROM resultat_statistique_par_fokontany rsf
JOIN fokontany fk
    ON fk.id = rsf.id
JOIN communes cm
    ON cm.id = fk.id_commune
GROUP BY
    rsf.id_election,
    rsf.election,
    cm.id,
    cm.code,
    cm.nom,
    cm.id_district,
    rsf.nom_district,
    rsf.id_region,
    rsf.nom_region
;


-- Local election only
CREATE OR REPLACE VIEW resultats_par_municipalite AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rcm.id_election) AS num_ligne,
    rcm.id_election,
    mc.id AS id_municipalite,
    mc.code AS code_municipalite,
    mc.nom AS nom_municipalite,
    rcm.id_candidat,
    rcm.numero_candidat,
    rcm.information_candidat,
    rcm.id_entite_politique,
    rcm.nom_entite_politique,
    rcm.description_entite_politique,
    SUM(rcm.voix_candidat) AS voix_candidat,
    rcm.chemin_photo_candidat
FROM resultats_par_commune rcm
JOIN communes cm
    ON rcm.id_commune = cm.id
JOIN municipalites mc
    ON cm.id_municipalite = mc.id
GROUP BY
    rcm.id_election,
    mc.id,
    mc.code,
    mc.nom,
    rcm.numero_candidat,
    rcm.id_candidat,
    rcm.information_candidat,
    rcm.id_entite_politique,
    rcm.nom_entite_politique,
    rcm.description_entite_politique,
    rcm.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_municipalite AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rstcm.id_election) AS num_ligne,
    rstcm.id_election,
    rstcm.election,
    mc.id,
    mc.code,
    mc.nom,
    mc.id_district,
    mc.nom_district,
    rstcm.id_region,
    rstcm.nom_region,
    SUM(rstcm.inscrits) AS inscrits,
    SUM(rstcm.homme_moins_36) AS homme_moins_36,
    SUM(rstcm.femme_moins_36) AS femme_moins_36,
    SUM(rstcm.homme_36_plus) AS homme_36_plus,
    SUM(rstcm.femme_36_plus) AS femme_36_plus,
    SUM(rstcm.handicapes) AS handicapes,
    SUM(rstcm.malvoyants) AS malvoyants,
    SUM(rstcm.blancs) AS blancs,
    SUM(rstcm.nuls) AS nuls,
    SUM(rstcm.exprimes) AS exprimes,
    SUM(rstcm.importes) AS importes,
    SUM(rstcm.nombre_bv) AS nombre_bv,
    SUM(rstcm.nombre_total_bv) AS nombre_total_bv,
    SUM(rstcm.nombre_alertes) AS nombre_alertes
FROM resultat_statistique_par_commune rstcm
JOIN communes cm
    ON cm.id = rstcm.id
JOIN municipalites mc
    ON mc.id = cm.id_municipalite
GROUP BY
    rstcm.id_election,
    rstcm.election,
    mc.id,
    mc.code,
    mc.nom,
    mc.id_district,
    mc.nom_district,
    rstcm.id_region,
    rstcm.nom_region
;


-- Presidential election and legislative election only
CREATE OR REPLACE VIEW resultats_par_district AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rcm.id_election) AS num_ligne,
    rcm.id_election,
    d.id AS id_district,
    d.code AS code_district,
    d.nom AS nom_district,
    rcm.id_candidat,
    rcm.numero_candidat,
    rcm.information_candidat,
    rcm.id_entite_politique,
    rcm.nom_entite_politique,
    rcm.description_entite_politique,
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
    rcm.description_entite_politique,
    rcm.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_district AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsc.id_election) AS num_ligne,
    rsc.id_election,
    rsc.election,
    d.id,
    d.code,
    d.nom,
    d.id_region,
    rsc.nom_region,
    SUM(rsc.inscrits) AS inscrits,
    SUM(rsc.homme_moins_36) AS homme_moins_36,
    SUM(rsc.femme_moins_36) AS femme_moins_36,
    SUM(rsc.homme_36_plus) AS homme_36_plus,
    SUM(rsc.femme_36_plus) AS femme_36_plus,
    SUM(rsc.handicapes) AS handicapes,
    SUM(rsc.malvoyants) AS malvoyants,
    SUM(rsc.blancs) AS blancs,
    SUM(rsc.nuls) AS nuls,
    SUM(rsc.exprimes) AS exprimes,
    SUM(rsc.importes) AS importes,
    SUM(rsc.nombre_bv) AS nombre_bv,
    SUM(rsc.nombre_total_bv) AS nombre_total_bv,
    SUM(rsc.nombre_alertes) AS nombre_alertes
FROM resultat_statistique_par_commune rsc
JOIN communes cm
    ON cm.id = rsc.id
JOIN districts d
    ON d.id = cm.id_district
GROUP BY
    rsc.id_election,
    rsc.election,
    d.id,
    d.code,
    d.nom,
    d.id_region,
    rsc.nom_region
;


-- Presidential election only
CREATE OR REPLACE VIEW resultats_par_region AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rds.id_election) AS num_ligne,
    rds.id_election,
    r.id AS id_region,
    r.code AS code_region,
    r.nom AS nom_region,
    rds.id_candidat,
    rds.numero_candidat,
    rds.information_candidat,
    rds.id_entite_politique,
    rds.nom_entite_politique,
    rds.description_entite_politique,
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
    rds.description_entite_politique,
    rds.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_region AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsd.id_election) AS num_ligne,
    rsd.id_election,
    rsd.election,
    r.id,
    r.code,
    r.nom,
    SUM(rsd.inscrits) AS inscrits,
    SUM(rsd.homme_moins_36) AS homme_moins_36,
    SUM(rsd.femme_moins_36) AS femme_moins_36,
    SUM(rsd.homme_36_plus) AS homme_36_plus,
    SUM(rsd.femme_36_plus) AS femme_36_plus,
    SUM(rsd.handicapes) AS handicapes,
    SUM(rsd.malvoyants) AS malvoyants,
    SUM(rsd.blancs) AS blancs,
    SUM(rsd.nuls) AS nuls,
    SUM(rsd.exprimes) AS exprimes,
    SUM(rsd.importes) AS importes,
    SUM(rsd.nombre_bv) AS nombre_bv,
    SUM(rsd.nombre_total_bv) AS nombre_total_bv,
    SUM(rsd.nombre_alertes) AS nombre_alertes
FROM resultat_statistique_par_district rsd
JOIN districts d
    ON d.id = rsd.id
JOIN regions r
    ON r.id = d.id_region
GROUP BY
    rsd.id_election,
    rsd.election,
    r.id,
    r.code,
    r.nom
;


-- Presidential election only
CREATE OR REPLACE VIEW resultats_par_province AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rrg.id_election) AS num_ligne,
    rrg.id_election,
    p.id AS id_province,
    TO_CHAR(p.id) AS code_province,
    P.nom AS nom_province,
    rrg.id_candidat,
    rrg.numero_candidat,
    rrg.information_candidat,
    rrg.id_entite_politique,
    rrg.nom_entite_politique,
    rrg.description_entite_politique,
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
    rrg.description_entite_politique,
    rrg.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_province AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsr.id_election) AS num_ligne,
    rsr.id_election,
    rsr.election,
    p.id,
    TO_CHAR(p.id) AS code,
    p.nom,
    SUM(rsr.inscrits) AS inscrits,
    SUM(rsr.homme_moins_36) AS homme_moins_36,
    SUM(rsr.femme_moins_36) AS femme_moins_36,
    SUM(rsr.homme_36_plus) AS homme_36_plus,
    SUM(rsr.femme_36_plus) AS femme_36_plus,
    SUM(rsr.handicapes) AS handicapes,
    SUM(rsr.malvoyants) AS malvoyants,
    SUM(rsr.blancs) AS blancs,
    SUM(rsr.nuls) AS nuls,
    SUM(rsr.exprimes) AS exprimes,
    SUM(rsr.importes) AS importes,
    SUM(rsr.nombre_bv) AS nombre_bv,
    SUM(rsr.nombre_total_bv) AS nombre_total_bv,
    SUM(rsr.nombre_alertes) AS nombre_alertes
FROM resultat_statistique_par_region rsr
JOIN regions r
    ON r.id = rsr.id
JOIN provinces p
    ON p.id = r.id_province
GROUP BY
    rsr.id_election,
    rsr.election,
    p.id,
    p.nom
;


-- Presidential election only
CREATE OR REPLACE VIEW resultats_election AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rpp.id_election) AS num_ligne,
    rpp.id_election,
    '0' AS id_pays,
    '0' AS code_pays,
    'Madagascar' AS nom_pays,
    rpp.id_candidat,
    rpp.numero_candidat,
    rpp.information_candidat,
    rpp.id_entite_politique,
    rpp.nom_entite_politique,
    rpp.description_entite_politique,
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
    rpp.description_entite_politique,
    rpp.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_election AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsp.id_election) AS num_ligne,
    rsp.id_election,
    rsp.election,
    '0' AS id,
    '0' AS code,
    'Madagascar' AS nom,
    SUM(rsp.inscrits) AS inscrits,
    SUM(rsp.homme_moins_36) AS homme_moins_36,
    SUM(rsp.femme_moins_36) AS femme_moins_36,
    SUM(rsp.homme_36_plus) AS homme_36_plus,
    SUM(rsp.femme_36_plus) AS femme_36_plus,
    SUM(rsp.handicapes) AS handicapes,
    SUM(rsp.malvoyants) AS malvoyants,
    SUM(rsp.blancs) AS blancs,
    SUM(rsp.nuls) AS nuls,
    SUM(rsp.exprimes) AS exprimes,
    SUM(rsp.importes) AS importes,
    SUM(rsp.nombre_bv) AS nombre_bv,
    SUM(rsp.nombre_total_bv) AS nombre_total_bv,
    SUM(rsp.nombre_alertes) AS nombre_alertes
FROM resultat_statistique_par_province rsp
GROUP BY
    rsp.id_election,
    rsp.election
;