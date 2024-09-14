CREATE VIEW stat_utilisateur AS
SELECT
    u.id_role,
    r.nom AS nom_role,
    COUNT(u.id) AS nombre_utilisateur
FROM utilisateurs u
JOIN roles r ON u.id_role = r.id
GROUP BY u.id_role, r.nom;