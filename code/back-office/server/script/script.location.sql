CREATE TABLE provinces (
   id NUMBER,
   nom VARCHAR2(12) NOT NULL,
   PRIMARY KEY(id),
   CONSTRAINT unique_nom_province UNIQUE(nom)
);

CREATE TABLE regions (
   id NUMBER,
   id_province NUMBER NOT NULL,
   p_code VARCHAR2(10) NOT NULL,
   r_code VARCHAR2(10) NOT NULL,
   nom VARCHAR2(50) UNIQUE NOT NULL,
   geom MDSYS.SDO_GEOMETRY,
   PRIMARY KEY(id),
   CONSTRAINT unique_code_region UNIQUE(p_code, r_code),
   FOREIGN KEY(id_province) REFERENCES provinces(id)
);
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES (
    'REGIONS',                   -- The name of your table
    'GEOM',                   -- The name of the geometry column
    SDO_DIM_ARRAY(            -- The dimensional information for the geometry
        SDO_DIM_ELEMENT('X', -180, 180, 0.05),  -- X dimension (Longitude)
        SDO_DIM_ELEMENT('Y', -90, 90, 0.05)     -- Y dimension (Latitude)
    ),
    8307                      -- The SRID (Spatial Reference ID), 8307 corresponds to WGS 84
);
CREATE INDEX idx_region_geom ON regions(geom) INDEXTYPE IS MDSYS.SPATIAL_INDEX;
INSERT INTO regions
VALUES (
    1,
    1,
    'MDG11',
    '11',
    'Test region',
    MDSYS.SDO_GEOMETRY(
        2003,
        NULL,
        NULL,
        MDSYS.SDO_ELEM_INFO_ARRAY(1,1003,1003),
        MDSYS.SDO_ORDINATE_ARRAY(0,0,10,0,10,10,0,10,0,0,5,5,7,5,7,7,5,7,5,5)
    )
);

-- insert example data with multipolygon in the regions table
INSERT INTO regions
VALUES (
    1,
    1,
    'MDG12',
    '12',
    'Test region 2',
    MDSYS.SDO_GEOMETRY(
        2007,
        8307,
        NULL,
        MDSYS.SDO_ELEM_INFO_ARRAY(1,1003,1,19,2003,1),
        MDSYS.SDO_ORDINATE_ARRAY(0,0,10,0,10,10,0,10,0,0,5,5,7,5,7,7,5,7,5,5,0,0,10,0,10,10,0,10,0,0,5,5,7,5,7,7,5,7,5,5)
    )
);

SELECT 
    nom,
    SDO_GEOM.SDO_AREA(geom) AS area,
    SDO_GEOM.SDO_LENGTH(geom) AS perimeter
FROM 
    regions;
