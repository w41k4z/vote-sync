CREATE OR REPLACE VIEW v_pays AS
SELECT
    0 AS id,
    'Madagascar' AS nom,
    '0' AS code
FROM DUAL
;

CREATE OR REPLACE VIEW v_district_municipalites AS
SELECT
    d.id,
    d.code,
    d.id_region,
    mc.nom_district AS nom,
    d.geojson,
    d.etat
FROM municipalites mc
JOIN districts d
    ON mc.id_district = d.id
;

CREATE OR REPLACE VIEW stat_utilisateur AS
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

CREATE OR REPLACE VIEW liste_bv AS
SELECT
    bv.id,
    bv.code AS code_bv,
    bv.nom AS nom_bv,
    cv.id AS id_cv,
    cv.nom AS nom_cv,
    fk.id AS id_fokontany,
    fk.nom AS nom_fokontany,
    cm.id AS id_commune,
    cm.nom AS nom_commune,
    d.id AS id_district,
    d.nom AS nom_district,
    r.id AS id_region,
    r.nom AS nom_region,
    u.nom AS nom_operateur,
    u.prenom AS prenom_operateur,
    u.contact AS contact_operateur,
    u2.nom AS nom_membre_bv,
    u2.prenom AS prenom_membre_bv,
    u2.contact AS contact_membre_bv
FROM bv
JOIN cv
    ON bv.id_cv = cv.id
JOIN fokontany fk
    ON fk.id = cv.id_fokontany
JOIN communes cm
    ON fk.id_commune = cm.id
JOIN districts d
    ON cm.id_district = d.id
JOIN regions r
    ON d.id_region = r.id
LEFT JOIN utilisateurs u
    ON bv.id_operateur_validateur = u.id
LEFT JOIN utilisateurs u2
    ON bv.code = u2.identifiant
ORDER BY
    r.nom,
    d.nom,
    cm.nom,
    fk.nom,
    cv.nom,
    bv.code
;

CREATE OR REPLACE VIEW resultats_en_attente AS
SELECT
    rs.id,
    rs.id_election,
    e.nom AS nom_election,
    rs.id_bv,
    bv.nom AS nom_bv,
    u.identifiant AS identifiant_operateur_validateur,
    rs.inscrits,
    rs.blancs,
    rs.nuls,
    rs.etat
FROM resultats rs
JOIN elections e
    ON rs.id_election = e.id
JOIN bv
    ON rs.id_bv = bv.id
JOIN utilisateurs u
    ON bv.id_operateur_validateur = u.id
WHERE e.etat < 20 -- Election pas encore validé
;

CREATE OR REPLACE VIEW v_details_resultats AS
SELECT
    dr.*,
    c.id AS id_candidat,
    c.information AS information_candidat,
    ep.id AS id_entite_politique,
    ep.nom AS nom_entite_politique,
    ep.description AS description_entite_politique
FROM details_resultats dr
JOIN enregistrement_candidats ec
    ON dr.id_enregistrement_candidat = ec.id
JOIN candidats c
    ON ec.id_candidat = c.id
JOIN entites_politiques ep
    ON c.id_entite_politique = ep.id
;

-- expected to be used with current elections
CREATE OR REPLACE VIEW v_elections AS
SELECT
    e.*,
    ersbvi.nombre_bv,
    ersbvi.nombre_total_bv
FROM elections e
JOIN elections_resultats_bv_info ersbvi
    ON e.id = ersbvi.id
;


-- for current elections
CREATE OR REPLACE VIEW fokontany_total_bv_info AS
SELECT
    fk.id AS id_fokontany,
    (
        SELECT 
            COUNT(bv.id) 
        FROM bv
        JOIN cv
            ON bv.id_cv = cv.id
        WHERE cv.id_fokontany = fk.id
    ) AS nombre_total_bv
FROM fokontany fk
;
CREATE OR REPLACE VIEW fokontany_resultats_bv_info AS
SELECT
    rs.id_election,
    fk.id AS id_fokontany,
    COUNT(fk.id) AS nombre_bv
FROM resultats rs
JOIN elections e
    ON e.id = rs.id_election
JOIN bv
    ON rs.id_bv = bv.id
JOIN cv
    ON bv.id_cv = cv.id
JOIN fokontany fk
    ON cv.id_fokontany = fk.id
WHERE 
    rs.etat >= 20
    AND e.etat < 20
GROUP BY 
    rs.id_election, 
    fk.id
;

CREATE OR REPLACE VIEW commune_total_bv_info AS
SELECT
    cm.id AS id_commune,
    SUM(fktbvi.nombre_total_bv) AS nombre_total_bv
FROM fokontany_total_bv_info fktbvi
JOIN fokontany fk
    ON fktbvi.id_fokontany = fk.id
JOIN communes cm
    ON fk.id_commune = cm.id
GROUP BY cm.id
;
CREATE OR REPLACE VIEW commune_resultats_bv_info AS
SELECT
    fkrsbvi.id_election,
    cm.id AS id_commune,
    SUM(fkrsbvi.nombre_bv) AS nombre_bv
FROM fokontany_resultats_bv_info fkrsbvi
JOIN fokontany fk
    ON fkrsbvi.id_fokontany = fk.id
JOIN communes cm
    ON fk.id_commune = cm.id
GROUP BY 
    fkrsbvi.id_election, 
    cm.id
;

CREATE OR REPLACE VIEW municipalite_total_bv_info AS
SELECT
    mc.id AS id_municipalite,
    SUM(cmtbvi.nombre_total_bv) AS nombre_total_bv
FROM commune_total_bv_info cmtbvi
JOIN communes cm
    ON cmtbvi.id_commune = cm.id
JOIN municipalites mc
    ON cm.id_municipalite = mc.id
GROUP BY mc.id
;
CREATE OR REPLACE VIEW municipalite_resultats_bv_info AS
SELECT
    cmrsbvi.id_election,
    mc.id AS id_municipalite,
    SUM(cmrsbvi.nombre_bv) AS nombre_bv
FROM commune_resultats_bv_info cmrsbvi
JOIN communes cm
    ON cmrsbvi.id_commune = cm.id
JOIN municipalites mc
    ON cm.id_municipalite = mc.id
GROUP BY 
    cmrsbvi.id_election, 
    mc.id
;

CREATE OR REPLACE VIEW district_total_bv_info AS
SELECT
    d.id AS id_district,
    SUM(cmtbvi.nombre_total_bv) AS nombre_total_bv
FROM commune_total_bv_info cmtbvi
JOIN communes cm
    ON cmtbvi.id_commune = cm.id
JOIN districts d
    ON cm.id_district = d.id
GROUP BY d.id
;
CREATE OR REPLACE VIEW district_resultats_bv_info AS
SELECT
    cmrsbvi.id_election,
    d.id AS id_disctrict,
    SUM(cmrsbvi.nombre_bv) AS nombre_bv
FROM commune_resultats_bv_info cmrsbvi
JOIN communes cm
    ON cmrsbvi.id_commune = cm.id
JOIN districts d
    ON cm.id_district = d.id
GROUP BY 
    cmrsbvi.id_election, 
    d.id
;

CREATE OR REPLACE VIEW region_total_bv_info AS
SELECT
    r.id AS id_region,
    (
        SELECT 
            COUNT(bv.id) 
        FROM bv
        JOIN cv
            ON bv.id_cv = cv.id
        JOIN fokontany fk
            ON cv.id_fokontany = fk.id
        JOIN communes cm
            ON fk.id_commune = cm.id
        JOIN districts d
            ON cm.id_district = d.id
        WHERE d.id_region = r.id
    ) AS nombre_total_bv
FROM regions r
;
CREATE OR REPLACE VIEW province_total_bv_info AS
SELECT
    p.id AS id_province,
    (
        SELECT 
            COUNT(bv.id) 
        FROM bv
        JOIN cv
            ON bv.id_cv = cv.id
        JOIN fokontany fk
            ON cv.id_fokontany = fk.id
        JOIN communes cm
            ON fk.id_commune = cm.id
        JOIN districts d
            ON cm.id_district = d.id
        JOIN regions r
            ON d.id_region = r.id
        WHERE r.id_province = p.id
    ) AS nombre_total_bv
FROM provinces p
;