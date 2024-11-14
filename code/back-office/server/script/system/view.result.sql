CREATE OR REPLACE VIEW stat_electeur_par_bv AS
SELECT
    ee.id_election,
    ee.id_bv,
    SUM(homme_moins_36) AS homme_moins_36,
    SUM(femme_moins_36) AS femme_moins_36,
    SUM(homme_36_plus) AS homme_36_plus,
    SUM(homme_36_plus) AS femme_36_plus,
    SUM(handicape) AS handicape,
    SUM(malvoyant) AS malvoyant
FROM enregistrement_electeurs ee
JOIN v_electeurs_details ved
    ON ved.id = ee.id_electeur
WHERE ee.vote >= 20
GROUP BY
    ee.id_election,
    ee.id_bv
;

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
    ep.description AS description_entite_politique,
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
WHERE r.etat >= 20
ORDER BY
    r.id_election,
    ec.numero_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_bv AS
SELECT
    ROW_NUMBER() OVER(ORDER BY r.id_election) AS num_ligne,
    r.id_election,
    r.id_bv AS id,
    bv.code AS code_bv,
    bv.nom AS nom_bv,
    lbv.id_fokontany, -- id for filter
    lbv.nom_fokontany, -- name for display
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
    r.handicape,
    r.malvoyant,
    r.blancs,
    r.nuls,
    (
        r.homme_moins_36 +
        r.femme_moins_36 +
        r.homme_36_plus +
        r.femme_36_plus
    ) - (r.blancs + r.nuls) AS exprimes,
    r.importe
FROM resultats r
JOIN bv
    ON r.id_bv = bv.id
JOIN liste_bv lbv
    ON lbv.id = bv.id
WHERE r.etat >= 20
;
CREATE OR REPLACE VIEW bv_resultats AS
SELECT
    rsbv.id_election,
    bv.id,
    bv.code,
    bv.nom,
    rsbv.id_fokontany,
    rsbv.nom_fokontany,
    rsbv.id_commune,
    rsbv.nom_commune,
    rsbv.id_district,
    rsbv.nom_district,
    rsbv.id_region,
    rsbv.nom_region,
    rsbv.inscrits,
    rsbv.homme_moins_36,
    rsbv.femme_moins_36,
    rsbv.homme_36_plus,
    rsbv.femme_36_plus,
    rsbv.handicape,
    rsbv.malvoyant,
    rsbv.blancs,
    rsbv.nuls,
    rsbv.exprimes,
    rsbv.importe
FROM bv
JOIN resultat_statistique_par_bv rsbv
    ON bv.id = rsbv.id_bv
;
CREATE OR REPLACE VIEW bv_resultats_election_locale AS
SELECT
    bvr.id_election,
    bvr.id,
    bvr.code,
    bvr.nom,
    bvr.id_fokontany,
    bvr.nom_fokontany,
    mc.id AS id_municipalite,
    mc.nom AS nom_municipalite,
    bvr.id_district,
    mc.nom_district,
    bvr.id_region,
    bvr.nom_region,
    bvr.inscrits,
    bvr.homme_moins_36,
    bvr.femme_moins_36,
    bvr.homme_36_plus,
    bvr.femme_36_plus,
    bvr.handicape,
    bvr.malvoyant,
    bvr.blancs,
    bvr.nuls,
    bvr.exprimes,
    bvr.importe
FROM bv_resultats bvr
JOIN communes cm
    ON bvr.id_commune = cm.id
JOIN municipalites mc
    ON cm.id_municipalite = mc.id
;
CREATE OR REPLACE VIEW alertes_stat_par_bv AS
SELECT
    ROW_NUMBER() OVER(ORDER BY a.id_bv) AS id,
    a.id_election,
    a.id_bv,
    COUNT(a.titre) AS nombre_alertes
FROM alertes a
GROUP BY
    a.id_election,
    a.id_bv
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
    ROW_NUMBER() OVER(ORDER BY rsbv.id_election) AS id,
    rsbv.id_election,
    fk.id AS id_fokontany,
    fk.code AS code_fokontany,
    fk.nom AS nom_fokontany,
    fk.id_commune,
    rsbv.nom_commune,
    rsbv.id_district,
    rsbv.nom_district,
    rsbv.id_region,
    rsbv.nom_region,
    SUM(rsbv.inscrits) AS inscrits,
    SUM(rsbv.homme_moins_36) AS homme_moins_36,
    SUM(rsbv.femme_moins_36) AS femme_moins_36,
    SUM(rsbv.homme_36_plus) AS homme_36_plus,
    SUM(rsbv.femme_36_plus) AS femme_36_plus,
    SUM(rsbv.handicape) AS handicape,
    SUM(rsbv.malvoyant) AS malvoyant,
    SUM(rsbv.blancs) AS blancs,
    SUM(rsbv.nuls) AS nuls,
    SUM(rsbv.exprimes) AS exprimes,
    SUM(rsbv.importe) AS importes,
    COUNT(fk.id) AS nombre_bv,
    fktbvi.nombre_total_bv
FROM resultat_statistique_par_bv rsbv
JOIN bv
    ON rsbv.id_bv = bv.id
JOIN cv
    ON bv.id_cv = cv.id
JOIN fokontany fk
    ON fk.id = cv.id_fokontany
JOIN fokontany_total_bv_info fktbvi
    ON fktbvi.id_fokontany = fk.id
GROUP BY
    rsbv.id_election,
    fk.id,
    fk.code,
    fk.nom,
    fk.id_commune,
    rsbv.nom_commune,
    rsbv.id_district,
    rsbv.nom_district,
    rsbv.id_region,
    rsbv.nom_region,
    fktbvi.nombre_total_bv
;
CREATE OR REPLACE VIEW fokontany_resultats AS
SELECT
    rsfk.id_election,
    fk.id,
    fk.code,
    fk.nom,
    fk.id_commune,
    rsfk.nom_commune,
    rsfk.id_district,
    rsfk.nom_district,
    rsfk.id_region,
    rsfk.nom_region,
    rsfk.inscrits,
    rsfk.homme_moins_36,
    rsfk.femme_moins_36,
    rsfk.homme_36_plus,
    rsfk.femme_36_plus,
    rsfk.handicape,
    rsfk.malvoyant,
    rsfk.blancs,
    rsfk.nuls,
    rsfk.exprimes,
    rsfk.importes,
    rsfk.nombre_bv,
    rsfk.nombre_total_bv
FROM resultat_statistique_par_fokontany rsfk
JOIN fokontany fk
    ON fk.id = rsfk.id_fokontany
;
CREATE OR REPLACE VIEW fokontany_resultats_election_locale AS
SELECT
    fkr.id_election,
    fkr.id,
    fkr.code,
    fkr.nom,
    mc.id AS id_municipalite,
    mc.nom AS nom_municipalite,
    fkr.id_district,
    mc.nom_district,
    fkr.id_region,
    fkr.nom_region,
    fkr.inscrits,
    fkr.homme_moins_36,
    fkr.femme_moins_36,
    fkr.homme_36_plus,
    fkr.femme_36_plus,
    fkr.handicape,
    fkr.malvoyant,
    fkr.blancs,
    fkr.nuls,
    fkr.exprimes
FROM fokontany_resultats fkr
JOIN communes cm
    ON fkr.id_commune = cm.id
JOIN municipalites mc
    ON cm.id_municipalite = mc.id
GROUP BY
    fkr.id_election,
    fkr.id,
    fkr.code,
    fkr.nom,
    mc.id,
    mc.nom,
    fkr.id_district,
    mc.nom_district,
    fkr.id_region,
    fkr.nom_region,
    fkr.inscrits,
    fkr.homme_moins_36,
    fkr.femme_moins_36,
    fkr.homme_36_plus,
    fkr.femme_36_plus,
    fkr.handicape,
    fkr.malvoyant,
    fkr.blancs,
    fkr.nuls,
    fkr.exprimes
;
CREATE OR REPLACE VIEW alertes_stat_par_fokontany AS
SELECT
    ROW_NUMBER() OVER(ORDER BY fk.id) AS id,
    a_bv.id_election,
    fk.id AS id_fokontany,
    SUM(a_bv.nombre_alertes) AS nombre_alertes
FROM alertes_stat_par_bv a_bv
JOIN cv
    ON a_bv.id_bv = cv.id
JOIN fokontany fk
    ON cv.id_fokontany = fk.id
GROUP BY
    a_bv.id_election,
    fk.id
;



-- Presidential election and legislative election only
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
    ROW_NUMBER() OVER(ORDER BY rsf.id_election) AS id,
    rsf.id_election,
    cm.id AS id_commune,
    cm.code AS code_commune,
    cm.nom AS nom_commune,
    cm.id_district,
    rsf.nom_district,
    rsf.id_region,
    rsf.nom_region,
    SUM(rsf.inscrits) AS inscrits,
    SUM(rsf.homme_moins_36) AS homme_moins_36,
    SUM(rsf.femme_moins_36) AS femme_moins_36,
    SUM(rsf.homme_36_plus) AS homme_36_plus,
    SUM(rsf.femme_36_plus) AS femme_36_plus,
    SUM(rsf.handicape) AS handicape,
    SUM(rsf.malvoyant) AS malvoyant,
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
    cm.nom,
    cm.id_district,
    rsf.nom_district,
    rsf.id_region,
    rsf.nom_region
;
CREATE OR REPLACE VIEW communes_resultats AS
SELECT
    rscm.id_election,
    cm.id,
    cm.code,
    cm.nom,
    cm.id_district,
    rscm.nom_district,
    rscm.id_region,
    rscm.nom_region,
    rscm.inscrits,
    rscm.homme_moins_36,
    rscm.femme_moins_36,
    rscm.homme_36_plus,
    rscm.femme_36_plus,
    rscm.handicape,
    rscm.malvoyant,
    rscm.blancs,
    rscm.nuls,
    rscm.exprimes
FROM communes cm
JOIN resultat_statistique_par_commune rscm
    ON cm.id = rscm.id_commune
;
CREATE OR REPLACE VIEW alertes_stat_par_commune AS
SELECT
    ROW_NUMBER() OVER(ORDER BY cm.id) AS id,
    a_fk.id_election,
    cm.id AS id_commune,
    SUM(a_fk.nombre_alertes) AS nombre_alertes
FROM alertes_stat_par_fokontany a_fk
JOIN fokontany fk
    ON a_fk.id_fokontany = fk.id
JOIN communes cm
    ON fk.id_commune = cm.id
GROUP BY
    a_fk.id_election,
    cm.id
;


-- Presidential election and local election only
CREATE OR REPLACE VIEW resultats_par_municipalite AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rfk.id_election) AS id,
    rfk.id_election,
    mc.id AS id_municipalite,
    mc.code AS code_municipalite,
    mc.nom AS nom_municipalite,
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
JOIN municipalites mc
    ON cm.id_municipalite = mc.id
GROUP BY
    rfk.id_election,
    mc.id,
    mc.code,
    mc.nom,
    rfk.numero_candidat,
    rfk.id_candidat,
    rfk.information_candidat,
    rfk.id_entite_politique,
    rfk.nom_entite_politique,
    rfk.description_entite_politique,
    rfk.chemin_photo_candidat
;
CREATE OR REPLACE VIEW resultat_statistique_par_municipalite AS
SELECT
    ROW_NUMBER() OVER(ORDER BY rsf.id_election) AS id,
    rsf.id_election,
    mc.id AS id_municipalite,
    mc.code AS code_municipalite,
    mc.nom AS nom_municipalite,
    mc.id_district,
    mc.nom_district,
    rsf.id_region,
    rsf.nom_region,
    SUM(rsf.inscrits) AS inscrits,
    SUM(rsf.homme_moins_36) AS homme_moins_36,
    SUM(rsf.femme_moins_36) AS femme_moins_36,
    SUM(rsf.homme_36_plus) AS homme_36_plus,
    SUM(rsf.femme_36_plus) AS femme_36_plus,
    SUM(rsf.handicape) AS handicape,
    SUM(rsf.malvoyant) AS malvoyant,
    SUM(rsf.blancs) AS blancs,
    SUM(rsf.nuls) AS nuls,
    SUM(rsf.exprimes) AS exprimes
FROM resultat_statistique_par_fokontany rsf
JOIN fokontany fk
    ON fk.id = rsf.id_fokontany
JOIN communes cm
    ON cm.id = fk.id_commune
JOIN municipalites mc
    ON mc.id = cm.id_municipalite
GROUP BY
    rsf.id_election,
    mc.id,
    mc.code,
    mc.nom,
    mc.id_district,
    mc.nom_district,
    rsf.id_region,
    rsf.nom_region
;
CREATE OR REPLACE VIEW municipalites_resultats AS
SELECT
    rsmc.id_election,
    mc.id,
    mc.code,
    mc.nom,
    mc.id_district,
    mc.nom_district,
    rsmc.id_region,
    rsmc.nom_region,
    rsmc.inscrits,
    rsmc.homme_moins_36,
    rsmc.femme_moins_36,
    rsmc.homme_36_plus,
    rsmc.femme_36_plus,
    rsmc.handicape,
    rsmc.malvoyant,
    rsmc.blancs,
    rsmc.nuls,
    rsmc.exprimes
FROM municipalites mc
JOIN resultat_statistique_par_municipalite rsmc
    ON mc.id = rsmc.id_municipalite
;
CREATE OR REPLACE VIEW alertes_stat_par_municipalite AS
SELECT
    ROW_NUMBER() OVER(ORDER BY mc.id) AS id,
    a_fk.id_election,
    mc.id AS id_municipalite,
    SUM(a_fk.nombre_alertes) AS nombre_alertes
FROM alertes_stat_par_fokontany a_fk
JOIN fokontany fk
    ON a_fk.id_fokontany = fk.id
JOIN communes cm
    ON fk.id_commune = cm.id
JOIN municipalites mc
    ON cm.id_municipalite = mc.id
GROUP BY
    a_fk.id_election,
    mc.id
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
    ROW_NUMBER() OVER(ORDER BY rsc.id_election) AS id,
    rsc.id_election,
    d.id AS id_district,
    d.code AS code_district,
    d.nom AS nom_district,
    d.id_region,
    rsc.nom_region,
    SUM(rsc.inscrits) AS inscrits,
    SUM(rsc.homme_moins_36) AS homme_moins_36,
    SUM(rsc.femme_moins_36) AS femme_moins_36,
    SUM(rsc.homme_36_plus) AS homme_36_plus,
    SUM(rsc.femme_36_plus) AS femme_36_plus,
    SUM(rsc.handicape) AS handicape,
    SUM(rsc.malvoyant) AS malvoyant,
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
    d.nom,
    d.id_region,
    rsc.nom_region
;
CREATE OR REPLACE VIEW districts_resultats AS
SELECT
    rsd.id_election,
    d.id,
    d.code,
    d.nom,
    d.id_region,
    rsd.nom_region,
    rsd.inscrits,
    rsd.homme_moins_36,
    rsd.femme_moins_36,
    rsd.homme_36_plus,
    rsd.femme_36_plus,
    rsd.handicape,
    rsd.malvoyant,
    rsd.blancs,
    rsd.nuls,
    rsd.exprimes
FROM districts d
JOIN resultat_statistique_par_district rsd
    ON d.id = rsd.id_district
;
CREATE OR REPLACE VIEW alertes_stat_par_district AS
SELECT
    ROW_NUMBER() OVER(ORDER BY d.id) AS id,
    a_cm.id_election,
    d.id AS id_district,
    SUM(a_cm.nombre_alertes) AS nombre_alertes
FROM alertes_stat_par_commune a_cm
JOIN communes cm
    ON a_cm.id_commune = cm.id
JOIN districts d
    ON cm.id_district = d.id
GROUP BY
    a_cm.id_election,
    d.id
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
    ROW_NUMBER() OVER(ORDER BY rsd.id_election) AS id,
    rsd.id_election,
    r.id AS id_region,
    r.code AS code_region,
    r.nom AS nom_region,
    SUM(rsd.inscrits) AS inscrits,
    SUM(rsd.homme_moins_36) AS homme_moins_36,
    SUM(rsd.femme_moins_36) AS femme_moins_36,
    SUM(rsd.homme_36_plus) AS homme_36_plus,
    SUM(rsd.femme_36_plus) AS femme_36_plus,
    SUM(rsd.handicape) AS handicape,
    SUM(rsd.malvoyant) AS malvoyant,
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
CREATE OR REPLACE VIEW regions_resultats AS
SELECT
    rsr.id_election,
    r.id,
    r.code,
    r.nom,
    rsr.inscrits,
    rsr.homme_moins_36,
    rsr.femme_moins_36,
    rsr.homme_36_plus,
    rsr.femme_36_plus,
    rsr.handicape,
    rsr.malvoyant,
    rsr.blancs,
    rsr.nuls,
    rsr.exprimes
FROM regions r
JOIN resultat_statistique_par_region rsr
    ON r.id = rsr.id_region
;
CREATE OR REPLACE VIEW alertes_stat_par_region AS
SELECT
    ROW_NUMBER() OVER(ORDER BY r.id) AS id,
    a_cd.id_election,
    r.id AS id_region,
    SUM(a_cd.nombre_alertes) AS nombre_alertes
FROM alertes_stat_par_district a_cd
JOIN districts d
    ON a_cd.id_district = d.id
JOIN regions r
    ON d.id_region = r.id
GROUP BY
    a_cd.id_election,
    r.id
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
    ROW_NUMBER() OVER(ORDER BY rsr.id_election) AS id,
    rsr.id_election,
    p.id AS id_province,
    TO_CHAR(p.id) AS code_province,
    p.nom AS nom_province,
    SUM(rsr.inscrits) AS inscrits,
    SUM(rsr.homme_moins_36) AS homme_moins_36,
    SUM(rsr.femme_moins_36) AS femme_moins_36,
    SUM(rsr.homme_36_plus) AS homme_36_plus,
    SUM(rsr.femme_36_plus) AS femme_36_plus,
    SUM(rsr.handicape) AS handicape,
    SUM(rsr.malvoyant) AS malvoyant,
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
CREATE OR REPLACE VIEW provinces_resultats AS
SELECT
    rsp.id_election,
    p.id,
    TO_CHAR(p.id) AS code,
    p.nom,
    rsp.inscrits,
    rsp.homme_moins_36,
    rsp.femme_moins_36,
    rsp.homme_36_plus,
    rsp.femme_36_plus,
    rsp.handicape,
    rsp.malvoyant,
    rsp.blancs,
    rsp.nuls,
    rsp.exprimes
FROM provinces p
JOIN resultat_statistique_par_province rsp
    ON p.id = rsp.id_province
;
CREATE OR REPLACE VIEW alertes_stat_par_province AS
SELECT
    ROW_NUMBER() OVER(ORDER BY p.id) AS id,
    a_cd.id_election,
    p.id AS id_province,
    SUM(a_cd.nombre_alertes) AS nombre_alertes
FROM alertes_stat_par_region a_cd
JOIN regions r
    ON a_cd.id_region = r.id
JOIN provinces p
    ON r.id_province = p.id
GROUP BY
    a_cd.id_election,
    p.id
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
    rsp.id_election AS id,
    rsp.id_election,
    '0' AS id_pays,
    'Madagascar' AS pays,
    '0' AS code,
    SUM(rsp.inscrits) AS inscrits,
    SUM(rsp.homme_moins_36) AS homme_moins_36,
    SUM(rsp.femme_moins_36) AS femme_moins_36,
    SUM(rsp.homme_36_plus) AS homme_36_plus,
    SUM(rsp.femme_36_plus) AS femme_36_plus,
    SUM(rsp.handicape) AS handicape,
    SUM(rsp.malvoyant) AS malvoyant,
    SUM(rsp.blancs) AS blancs,
    SUM(rsp.nuls) AS nuls,
    SUM(rsp.exprimes) AS exprimes
FROM resultat_statistique_par_province rsp
GROUP BY
    rsp.id_election
;
CREATE OR REPLACE VIEW global_resultats AS
SELECT
    rsg.id_election,
    '0' AS id,
    '0' AS code,
    'Madagascar' AS nom,
    rsg.inscrits,
    rsg.homme_moins_36,
    rsg.femme_moins_36,
    rsg.homme_36_plus,
    rsg.femme_36_plus,
    rsg.handicape,
    rsg.malvoyant,
    rsg.blancs,
    rsg.nuls,
    rsg.exprimes
FROM dual d
JOIN resultat_statistique_election rsg
    ON rsg.id_pays = '0'
;
CREATE OR REPLACE VIEW alertes_stat_par_election AS
SELECT
    ROW_NUMBER() OVER(ORDER BY a_cd.id_election) AS id,
    a_cd.id_election,
    '0' AS id_pays,
    SUM(a_cd.nombre_alertes) AS nombre_alertes
FROM alertes_stat_par_province a_cd
GROUP BY
    a_cd.id_election
;
-- for current elections
CREATE OR REPLACE VIEW elections_resultats_bv_info AS
SELECT
    e.id,
    COALESCE(COUNT(rs.id_bv), 0) AS nombre_bv,
    (SELECT COUNT(id) FROM bv) AS nombre_total_bv
FROM elections e
LEFT JOIN resultats rs
    ON e.id = rs.id_election
    AND rs.etat >= 20
WHERE e.etat < 20
GROUP BY e.id
;