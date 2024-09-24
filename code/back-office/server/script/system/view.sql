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
    r.nom AS nom_region
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
ORDER BY
    r.nom,
    d.nom,
    cm.nom,
    fk.nom,
    cv.nom,
    bv.code
;