CREATE TABLE provinces (
   id CHAR(1),
   nom VARCHAR2(12) NOT NULL,
   PRIMARY KEY(id),
   CONSTRAINT unique_nom_province UNIQUE(nom)
);

CREATE TABLE regions (
   id CHAR(2),
   id_province CHAR(1) NOT NULL,
   nom VARCHAR2(50) UNIQUE NOT NULL,
   geojson CLOB,
   PRIMARY KEY(id),
   FOREIGN KEY(id_province) REFERENCES provinces(id)
);
-- INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
-- VALUES (
--     'REGIONS',                   -- The name of your table
--     'GEOM',                   -- The name of the geometry column
--     SDO_DIM_ARRAY(            -- The dimensional information for the geometry
--         SDO_DIM_ELEMENT('X', -180, 180, 0.05),  -- X dimension (Longitude)
--         SDO_DIM_ELEMENT('Y', -90, 90, 0.05)     -- Y dimension (Latitude)
--     ),
--     4326                        -- The SRID (Spatial Reference ID)
-- );
-- CREATE INDEX idx_region_geom ON regions(geom) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE TABLE districts (
    id CHAR(4),
    id_region CHAR(2) NOT NULL,
    nom VARCHAR2(50) UNIQUE NOT NULL,
    geojson CLOB,
    PRIMARY KEY(id),
    FOREIGN KEY(id_region) REFERENCES regions(id)
);