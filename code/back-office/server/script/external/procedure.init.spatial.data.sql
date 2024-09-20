CREATE OR REPLACE PROCEDURE import_data AS
BEGIN
    import_regions;
    import_districts;
    import_communes;
    import_fokontany;
    import_cv;
    import_bv;

    EXECUTE IMMEDIATE 'TRUNCATE TABLE imported_regions';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE imported_districts';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE imported_communes';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE imported_fokontany';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE imported_cv';
    EXECUTE IMMEDIATE 'TRUNCATE TABLE imported_bv';
END;
/


CREATE OR REPLACE PROCEDURE import_regions AS
BEGIN
    INSERT INTO regions(code, id_province, nom, geojson) (
        SELECT code, id_province, nom, geojson
        FROM imported_regions
    );
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE import_districts AS
BEGIN
    INSERT INTO districts(code, id_region, nom, geojson) (
        SELECT im_d.code, r.id, im_d.nom, im_d.geojson
        FROM imported_districts im_d
        JOIN imported_regions im_r
            ON im_d.reg_pcode = im_r.p_code
        JOIN regions r
            ON im_r.code = r.code
    );
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE import_communes AS
BEGIN
    INSERT INTO communes(code, id_district, nom, geojson) (
        SELECT im_c.code, d.id, im_c.nom, im_c.geojson
        FROM imported_communes im_c
        JOIN imported_districts im_d
            ON im_c.dist_pcode = im_d.p_code
        JOIN districts d
            ON im_d.code = d.code
    );
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE import_fokontany AS
BEGIN
    INSERT INTO fokontany(code, id_commune, nom, geojson) (
        SELECT im_f.code, co.id, im_f.nom, im_f.geojson
        FROM imported_fokontany im_f
        JOIN imported_communes im_c
            ON im_f.com_pcode = im_c.p_code
        JOIN communes co
            ON im_c.code = co.code
    );
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE import_cv AS
BEGIN
    INSERT INTO cv(code, id_fokontany, nom) (
        SELECT im_cv.code, fk.id, im_cv.nom
        FROM imported_cv im_cv
        JOIN fokontany fk
            ON fk.code = SUBSTR(im_cv.code, 1, LENGTH(im_cv.code) - 2)
    );
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE import_bv AS
BEGIN
    INSERT INTO bv(code, id_cv, nom) (
        SELECT im_bv.code, cv.id, im_bv.nom
        FROM imported_bv im_bv
        JOIN cv cv
            ON cv.code = SUBSTR(im_bv.code, 1, LENGTH(im_bv.code) - 2)
    );
    COMMIT;
END;
/