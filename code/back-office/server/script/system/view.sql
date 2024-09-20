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
GROUP BY id_role, nom_role;

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
AND ROWNUM = 1;

CREATE VIEW resultats_par_bv AS
SELECT
    r.id_election,
    r.id_bv,
    dr.
FROM details_resultats dr
JOIN resultats r
    ON dr.id_resultat = r.id