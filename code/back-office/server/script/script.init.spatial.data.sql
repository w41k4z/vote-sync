CREATE TABLE imported_regions (
    id CHAR(2),
    id_province CHAR(1),
    p_code VARCHAR2(10),
    r_code VARCHAR2(10),
    nom VARCHAR2(50),
    geojson CLOB
);

CREATE TABLE imported_districts (
    id CHAR(4),
    p_code VARCHAR2(10),
    d_code VARCHAR2(10),
    reg_pcode VARCHAR2(10),
    r_code VARCHAR2(10),
    nom VARCHAR2(50),
    geojson CLOB
);