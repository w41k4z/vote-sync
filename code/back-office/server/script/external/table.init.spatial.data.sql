CREATE TABLE imported_regions (
    code CHAR(2),
    id_province CHAR(1),
    p_code VARCHAR2(10),
    r_code VARCHAR2(10),
    nom VARCHAR2(50),
    geojson CLOB
);

CREATE TABLE imported_districts (
    code CHAR(4),
    p_code VARCHAR2(10),
    d_code VARCHAR2(10),
    reg_pcode VARCHAR2(10),
    r_code VARCHAR2(10),
    nom VARCHAR2(50),
    geojson CLOB
);

CREATE TABLE imported_communes (
    code CHAR(6),
    p_code VARCHAR2(15),
    dist_pcode VARCHAR2(15),
    nom VARCHAR2(50),
    geojson CLOB
);

CREATE TABLE imported_fokontany (
    code CHAR(8),
    p_code VARCHAR2(20),
    com_pcode VARCHAR2(20),
    nom VARCHAR2(50),
    geojson CLOB
);

CREATE TABLE imported_cv (
    code CHAR(10),
    region VARCHAR2(50),
    district VARCHAR2(50),
    commune VARCHAR2(50),
    fokontany VARCHAR2(50),
    nom VARCHAR2(50)
);

CREATE TABLE imported_bv (
    code CHAR(12),
    nom VARCHAR2(50)
);