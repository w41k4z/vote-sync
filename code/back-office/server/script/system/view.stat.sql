CREATE OR REPLACE VIEW stat_electeurs AS
SELECT
    rsp.id_election,
    e.nom AS election,
    rsp.inscrits,
    (
        rsp.homme_moins_36 +
        rsp.femme_moins_36 +
        rsp.homme_36_plus +
        rsp.femme_36_plus
    ) AS enregistres,
    rsp.homme_moins_36,
    rsp.femme_moins_36,
    rsp.homme_36_plus,
    rsp.femme_36_plus,
    rsp.handicapes,
    rsp.malvoyants
FROM resultats_provisoires rsp
JOIN elections e
    ON e.id = rsp.id_election
ORDER BY e.date_fin ASC
FETCH FIRST 3 ROWS ONLY
;



CREATE OR REPLACE VIEW stat_fokontany AS
SELECT
    ROW_NUMBER() OVER(ORDER BY st.id_type_election) AS num_ligne,
    st.*,
    te.nom AS type_election,
    f.nom AS fokontany,
    f.geojson AS geojson
FROM (
    SELECT
        e.id_type_election,
        fkrsp.id_fokontany,
        AVG(fkrsp.inscrits) AS inscrits,
        AVG(
            fkrsp.homme_moins_36 +
            fkrsp.femme_moins_36 +
            fkrsp.homme_36_plus +
            fkrsp.femme_36_plus
        ) AS enregistres,
        AVG(fkrsp.importes) AS importes,
        AVG(fkrsp.nombre_bv) AS nombre_bv,
        AVG(fkrsp.nombre_total_bv) AS nombre_total_bv,
        AVG(fkrsp.nombre_alertes) AS nombre_alertes
    FROM fokontany_resultats_provisoires fkrsp
    JOIN elections e
        ON e.id = fkrsp.id_election
    GROUP BY 
        e.id_type_election,
        fkrsp.id_fokontany
) st
JOIN type_elections te
    ON te.id = st.id_type_election
JOIN fokontany f
    ON f.id = st.id_fokontany
;
CREATE OR REPLACE VIEW stat_global_fokontany AS
SELECT
    ROW_NUMBER() OVER(ORDER BY st.id_fokontany) AS num_ligne,
    st.*,
    f.nom AS fokontany,
    f.geojson AS geojson
FROM (
    SELECT
        fkrsp.id_fokontany,
        AVG(fkrsp.inscrits) AS inscrits,
        AVG(
            fkrsp.homme_moins_36 +
            fkrsp.femme_moins_36 +
            fkrsp.homme_36_plus +
            fkrsp.femme_36_plus
        ) AS enregistres,
        AVG(fkrsp.importes) AS importes,
        AVG(fkrsp.nombre_bv) AS nombre_bv,
        AVG(fkrsp.nombre_total_bv) AS nombre_total_bv,
        AVG(fkrsp.nombre_alertes) AS nombre_alertes
    FROM fokontany_resultats_provisoires fkrsp
    GROUP BY 
        fkrsp.id_fokontany
) st
JOIN fokontany f
    ON f.id = st.id_fokontany
;



CREATE OR REPLACE VIEW stat_commune AS
SELECT
    ROW_NUMBER() OVER(ORDER BY st.id_type_election) AS num_ligne,
    st.*,
    te.nom AS type_election,
    cm.nom AS commune,
    cm.geojson AS geojson
FROM (
    SELECT
        e.id_type_election,
        cmrsp.id_commune,
        AVG(cmrsp.inscrits) AS inscrits,
        AVG(
            cmrsp.homme_moins_36 +
            cmrsp.femme_moins_36 +
            cmrsp.homme_36_plus +
            cmrsp.femme_36_plus
        ) AS enregistres,
        AVG(cmrsp.importes) AS importes,
        AVG(cmrsp.nombre_bv) AS nombre_bv,
        AVG(cmrsp.nombre_total_bv) AS nombre_total_bv,
        AVG(cmrsp.nombre_alertes) AS nombre_alertes
    FROM communes_resultats_provisoires cmrsp
    JOIN elections e
        ON e.id = cmrsp.id_election
    GROUP BY 
        e.id_type_election,
        cmrsp.id_commune
) st
JOIN type_elections te
    ON te.id = st.id_type_election
JOIN communes cm
    ON cm.id = st.id_commune
;
CREATE OR REPLACE VIEW stat_global_commune AS
SELECT
    ROW_NUMBER() OVER(ORDER BY st.id_commune) AS num_ligne,
    st.*,
    cm.nom AS commune,
    cm.geojson AS geojson
FROM (
    SELECT
        cmrsp.id_commune,
        AVG(cmrsp.inscrits) AS inscrits,
        AVG(
            cmrsp.homme_moins_36 +
            cmrsp.femme_moins_36 +
            cmrsp.homme_36_plus +
            cmrsp.femme_36_plus
        ) AS enregistres,
        AVG(cmrsp.importes) AS importes,
        AVG(cmrsp.nombre_bv) AS nombre_bv,
        AVG(cmrsp.nombre_total_bv) AS nombre_total_bv,
        AVG(cmrsp.nombre_alertes) AS nombre_alertes
    FROM communes_resultats_provisoires cmrsp
    GROUP BY 
        cmrsp.id_commune
) st
JOIN communes cm
    ON cm.id = st.id_commune
;



CREATE OR REPLACE VIEW stat_district AS
SELECT
    ROW_NUMBER() OVER(ORDER BY st.id_type_election) AS num_ligne,
    st.*,
    te.nom AS type_election,
    d.nom AS district,
    d.geojson AS geojson
FROM (
    SELECT
        e.id_type_election,
        drsp.id_district,
        AVG(drsp.inscrits) AS inscrits,
        AVG(
            drsp.homme_moins_36 +
            drsp.femme_moins_36 +
            drsp.homme_36_plus +
            drsp.femme_36_plus
        ) AS enregistres,
        AVG(drsp.importes) AS importes,
        AVG(drsp.nombre_bv) AS nombre_bv,
        AVG(drsp.nombre_total_bv) AS nombre_total_bv,
        AVG(drsp.nombre_alertes) AS nombre_alertes
    FROM districts_resultats_provisoires drsp
    JOIN elections e
        ON e.id = drsp.id_election
    GROUP BY 
        e.id_type_election,
        drsp.id_district
) st
JOIN type_elections te
    ON te.id = st.id_type_election
JOIN districts d
    ON d.id = st.id_district
;
CREATE OR REPLACE VIEW stat_global_district AS
SELECT
    ROW_NUMBER() OVER(ORDER BY st.id_district) AS num_ligne,
    st.*,
    d.nom AS district,
    d.geojson AS geojson
FROM (
    SELECT
        drsp.id_district,
        AVG(drsp.inscrits) AS inscrits,
        AVG(
            drsp.homme_moins_36 +
            drsp.femme_moins_36 +
            drsp.homme_36_plus +
            drsp.femme_36_plus
        ) AS enregistres,
        AVG(drsp.importes) AS importes,
        AVG(drsp.nombre_bv) AS nombre_bv,
        AVG(drsp.nombre_total_bv) AS nombre_total_bv,
        AVG(drsp.nombre_alertes) AS nombre_alertes
    FROM districts_resultats_provisoires drsp
    GROUP BY 
        drsp.id_district
) st
JOIN districts d
    ON d.id = st.id_district
;



CREATE OR REPLACE VIEW stat_region AS
SELECT
    ROW_NUMBER() OVER(ORDER BY st.id_type_election) AS num_ligne,
    st.*,
    te.nom AS type_election,
    r.nom AS region,
    r.geojson AS geojson
FROM (
    SELECT
        e.id_type_election,
        rrsp.id_region,
        AVG(rrsp.inscrits) AS inscrits,
        AVG(
            rrsp.homme_moins_36 +
            rrsp.femme_moins_36 +
            rrsp.homme_36_plus +
            rrsp.femme_36_plus
        ) AS enregistres,
        AVG(rrsp.importes) AS importes,
        AVG(rrsp.nombre_bv) AS nombre_bv,
        AVG(rrsp.nombre_total_bv) AS nombre_total_bv,
        AVG(rrsp.nombre_alertes) AS nombre_alertes
    FROM regions_resultats_provisoires rrsp
    JOIN elections e
        ON e.id = rrsp.id_election
    GROUP BY 
        e.id_type_election,
        rrsp.id_region
) st
JOIN type_elections te
    ON te.id = st.id_type_election
JOIN regions r
    ON r.id = st.id_region
;
CREATE OR REPLACE VIEW stat_global_region AS
SELECT
    ROW_NUMBER() OVER(ORDER BY st.id_region) AS num_ligne,
    st.*,
    r.nom AS region,
    r.geojson AS geojson
FROM (
    SELECT
        rrsp.id_region,
        AVG(rrsp.inscrits) AS inscrits,
        AVG(
            rrsp.homme_moins_36 +
            rrsp.femme_moins_36 +
            rrsp.homme_36_plus +
            rrsp.femme_36_plus
        ) AS enregistres,
        AVG(rrsp.importes) AS importes,
        AVG(rrsp.nombre_bv) AS nombre_bv,
        AVG(rrsp.nombre_total_bv) AS nombre_total_bv,
        AVG(rrsp.nombre_alertes) AS nombre_alertes
    FROM regions_resultats_provisoires rrsp
    GROUP BY 
        rrsp.id_region
) st
JOIN regions r
    ON r.id = st.id_region
;