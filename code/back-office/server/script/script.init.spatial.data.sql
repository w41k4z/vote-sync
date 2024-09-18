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
    p_code VARCHAR2(15),
    c_code VARCHAR2(15),
    dist_pcode VARCHAR2(15),
    r_code VARCHAR2(15),
    nom VARCHAR2(50),
    geojson CLOB
);