CREATE OR REPLACE PROCEDURE import_regions AS
BEGIN
    INSERT INTO regions (
        SELECT id, id_province, nom, geojson
        FROM imported_regions
    );
END;
/

CREATE OR REPLACE PROCEDURE import_districts AS
BEGIN
    INSERT INTO districts (
        SELECT im_d.id, im_r.id, im_d.nom, im_d.geojson
        FROM imported_districts im_d
        JOIN imported_regions im_r
            ON im_d.reg_pcode = im_r.p_code
    );
END;
/