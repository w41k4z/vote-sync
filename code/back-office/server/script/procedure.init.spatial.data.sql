CREATE OR REPLACE PROCEDURE import_regions AS
BEGIN
    INSERT INTO regions(code, id_province, nom, geojson) (
        SELECT code, id_province, nom, geojson
        FROM imported_regions
    );
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
END;
/

CREATE OR REPLACE PROCEDURE import_communes AS
BEGIN
    INSERT INTO communes(code, id_district, nom, geojson) (
        SELECT im_d.code, r.id, im_d.nom, im_d.geojson
        FROM imported_districts im_d
        JOIN imported_regions im_r
            ON im_d.reg_pcode = im_r.p_code
        JOIN regions r
            ON im_r.code = r.code
    );
END;
/