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
    mc.nom_district AS nom,
    d.id_region,
    d.geojson,
    d.etat
FROM municipalites mc
JOIN districts d
    ON mc.id_district = d.id
;

CREATE OR REPLACE VIEW v_fokontany_municipalites AS
SELECT
    fk.id,
    fk.code,
    fk.nom,
    cm.id_municipalite,
    fk.geojson,
    fk.etat
FROM fokontany fk
JOIN communes cm
    ON cm.id = fk.id_commune
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
    rs.homme_moins_36,
    rs.femme_moins_36,
    rs.homme_36_plus,
    rs.femme_36_plus,
    rs.handicapes,
    rs.malvoyants,
    rs.blancs,
    rs.nuls,
    rs.etat
FROM resultats rs
JOIN elections e
    ON rs.id_election = e.id
    AND rs.etat < 20
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
    ep.description AS description_entite_politique,
    ec.chemin_photo
FROM details_resultats dr
JOIN enregistrement_candidats ec
    ON dr.id_enregistrement_candidat = ec.id
JOIN candidats c
    ON ec.id_candidat = c.id
JOIN entites_politiques ep
    ON c.id_entite_politique = ep.id
;

-- for current elections
CREATE OR REPLACE VIEW elections_resultats_bv_info AS
SELECT
    e.id,
    (
        SELECT COUNT(rs.id_bv) 
        FROM resultats rs 
        WHERE 
            rs.id_election = e.id
            AND rs.etat >= 20
    ) AS nombre_bv,
    (SELECT COUNT(id) FROM bv) AS nombre_total_bv
FROM elections e
WHERE e.etat < 20
GROUP BY e.id
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

CREATE OR REPLACE VIEW v_alertes AS
SELECT
    a.id,
    a.id_type_alerte,
    ta.nom AS nom_type_alerte,
    ta.niveau AS niveau_type_alerte,
    a.id_election,
    e.nom AS nom_election,
    bv.id AS id_bv,
    bv.nom AS nom_bv,
    fk.id AS id_fokontany,
    fk.nom AS nom_fokontany,
    cm.id AS id_commune,
    cm.nom AS nom_commune,
    d.id AS id_district,
    d.nom AS nom_district,
    r.id AS id_region,
    r.nom AS nom_region,
    a.date_alerte,
    a.description,
    a.etat
FROM alertes a
JOIN type_alertes ta
    ON a.id_type_alerte = ta.id
JOIN elections e
    ON a.id_election = e.id
    AND e.etat < 20
JOIN bv
    ON a.id_bv = bv.id
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
ORDER BY
    a.etat ASC,
    ta.niveau DESC
;