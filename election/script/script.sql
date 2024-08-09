CREATE DATABASE election;

CREATE TABLE candidat (
    id_candidat INTEGER PRIMARY KEY,
    nom VARCHAR(250) NOT NULL,
    parti_politique TEXT
);

CREATE TABLE bureau_de_vote (
    code_bv VARCHAR(12) PRIMARY KEY,
    rg_dst_cmm_fk_ctr_br TEXT NOT NULL UNIQUE
);
-- ANALAMANGA,AMBOHIDRATRIMO,AMBATO,AMBATO,CEG AMBATO,CEG AMBATO 1

CREATE TABLE resultat_bureau_de_vote (
    code_bv VARCHAR(12) PRIMARY KEY REFERENCES bureau_de_vote(code_bv),
    inscrits INTEGER NOT NULL,
    blancs INTEGER NOT NULL,
    nuls INTEGER NOT NULL
);

CREATE TABLE resultat_bureau_de_vote_candidat (
    code_bv VARCHAR(12) NOT NULL REFERENCES resultat_bureau_de_vote(code_bv),
    id_candidat INTEGER NOT NULL REFERENCES candidat(id_candidat),
    voix INTEGER
);


------------


CREATE VIEW resultat AS
SELECT
    rbv.code_bv,
    bv.rg_dst_cmm_fk_ctr_br,
    bv.arr[7] AS bureau_de_vote,
    rbvc.id_candidat,
    candidat.nom,
    candidat.parti_politique,
    rbvc.voix
FROM resultat_bureau_de_vote AS rbv
JOIN (
    SELECT
        code_bv,
        b.rg_dst_cmm_fk_ctr_br,
        STRING_TO_ARRAY(b.rg_dst_cmm_fk_ctr_br, ',') AS arr
    FROM bureau_de_vote b
) AS bv
    ON rbv.code_bv = bv.code_bv
JOIN resultat_bureau_de_vote_candidat AS rbvc
    ON rbv.code_bv = rbvc.code_bv
JOIN candidat
    ON candidat.id_candidat = rbvc.id_candidat;

CREATE VIEW resultat_avec_taux_participation AS
SELECT 
    resultat.code_bv,
    resultat.bureau_de_vote,
    ((SUM(resultat.voix) / rbv.inscrits) * 100) AS taux_de_participation
FROM resultat
JOIN resultat_bureau_de_vote AS rbv
    ON resultat.code_bv = rbv.code_bv
GROUP BY 
    resultat.code_bv,
    resultat.bureau_de_vote,
    rbv.inscrits
;


CREATE VIEW resultat_par_centre_de_vote AS
SELECT
    SUBSTRING(rs.code_bv, 0, 10) AS code_centre_de_vote,
    rs.rg_dst_cmm_fk_ctr_br[6] AS centre_de_vote,
    id_candidat,
    nom,
    parti_politique,
    SUM(voix) AS voix
FROM (
    SELECT
        code_bv,
        STRING_TO_ARRAY(rg_dst_cmm_fk_ctr_br, ',') AS rg_dst_cmm_fk_ctr_br,
        id_candidat,
        nom,
        parti_politique,
        voix
    FROM resultat
) AS rs
GROUP BY
    code_centre_de_vote,
    centre_de_vote,
    id_candidat,
    nom,
    parti_politique
;


CREATE VIEW resultat_par_fokotany AS
SELECT
    SUBSTRING(rs.code_bv, 0, 8) AS code_fokotany,
    rs.rg_dst_cmm_fk_ctr_br[5] AS fokotany,
    id_candidat,
    nom,
    parti_politique,
    SUM(voix) AS voix
FROM (
    SELECT
        code_bv,
        STRING_TO_ARRAY(rg_dst_cmm_fk_ctr_br, ',') AS rg_dst_cmm_fk_ctr_br,
        id_candidat,
        nom,
        parti_politique,
        voix
    FROM resultat
) AS rs
GROUP BY
    code_fokotany,
    fokotany,
    id_candidat,
    nom,
    parti_politique
;

CREATE VIEW resultat_par_commune AS
SELECT
    SUBSTRING(rs.code_bv, 0, 6) AS code_commune,
    rs.rg_dst_cmm_fk_ctr_br[4] AS commune,
    id_candidat,
    nom,
    parti_politique,
    SUM(voix) AS voix
FROM (
    SELECT
        code_bv,
        STRING_TO_ARRAY(rg_dst_cmm_fk_ctr_br, ',') AS rg_dst_cmm_fk_ctr_br,
        id_candidat,
        nom,
        parti_politique,
        voix
    FROM resultat
) AS rs
GROUP BY
    code_commune,
    commune,
    id_candidat,
    nom,
    parti_politique
;

CREATE VIEW resultat_par_district AS
SELECT
    SUBSTRING(rs.code_bv, 0, 4) AS code_district,
    rs.rg_dst_cmm_fk_ctr_br[3] AS district,
    id_candidat,
    nom,
    parti_politique,
    SUM(voix) AS voix
FROM (
    SELECT
        code_bv,
        STRING_TO_ARRAY(rg_dst_cmm_fk_ctr_br, ',') AS rg_dst_cmm_fk_ctr_br,
        id_candidat,
        nom,
        parti_politique,
        voix
    FROM resultat
) AS rs
GROUP BY
    code_district,
    district,
    id_candidat,
    nom,
    parti_politique
;

CREATE VIEW resultat_par_region AS
SELECT
    SUBSTRING(rs.code_bv, 0, 2) AS code_region,
    rs.rg_dst_cmm_fk_ctr_br[3] AS region,
    id_candidat,
    nom,
    parti_politique,
    SUM(voix) AS voix
FROM (
    SELECT
        code_bv,
        STRING_TO_ARRAY(rg_dst_cmm_fk_ctr_br, ',') AS rg_dst_cmm_fk_ctr_br,
        id_candidat,
        nom,
        parti_politique,
        voix
    FROM resultat
) AS rs
GROUP BY
    code_region,
    region,
    id_candidat,
    nom,
    parti_politique
;

CREATE VIEW resultat_par_province AS
SELECT
    SUBSTRING(rs.code_bv, 0, 1) AS code_province,
    id_candidat,
    nom,
    parti_politique,
    SUM(voix) AS voix
FROM (
    SELECT
        code_bv,
        id_candidat,
        nom,
        parti_politique,
        voix
    FROM resultat
) AS rs
GROUP BY
    code_province,
    id_candidat,
    nom,
    parti_politique
;
