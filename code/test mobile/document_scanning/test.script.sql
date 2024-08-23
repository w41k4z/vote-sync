CREATE TABLE point_table (
    name VARCHAR2(100),
    coordination SDO_GEOMETRY
);

INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES (
    'POINT_TABLE',                   -- The name of your table
    'COORDINATION',                   -- The name of the geometry column
    SDO_DIM_ARRAY(            -- The dimensional information for the geometry
        SDO_DIM_ELEMENT('X', -180, 180, 0.05),  -- X dimension (Longitude)
        SDO_DIM_ELEMENT('Y', -90, 90, 0.05)     -- Y dimension (Latitude)
    ),
    8307                      -- The SRID (Spatial Reference ID), 8307 corresponds to WGS 84
);

CREATE INDEX point_table_coordination_idx ON point_table(coordination) INDEXTYPE IS MDSYS.SPATIAL_INDEX;


---------------------------------------


INSERT INTO point_table (name, coordination) 
VALUES (
    'BV A', 
    SDO_GEOMETRY(
        2001,          -- 2001 indicates a point geometry
        8307,          -- SRID, for WGS 84 use 8307
        SDO_POINT_TYPE(47.5240020601191, -18.87508256515859, NULL),  -- X (longitude), Y (latitude), Z (elevation)
        NULL,          -- Null for the SDO_ELEM_INFO_ARRAY (not needed for a point)
        NULL           -- Null for the SDO_ORDINATE_ARRAY (not needed for a point)
    )
);
INSERT INTO point_table (name, coordination) 
VALUES (
    'BV B', 
    SDO_GEOMETRY(
        2001,
        8307,
        SDO_POINT_TYPE(47.523290893453975, -18.873037075208714, NULL),
        NULL,
        NULL
    )
);

position to test: -18.872107676573158, 47.523808400509736


-- Find the nearest point to a given location
SELECT name
FROM point_table
WHERE SDO_NN(
    coordination, 
    SDO_GEOMETRY(2001, 8307, SDO_POINT_TYPE(47.523808400509736, -18.872107676573158, NULL), NULL, NULL), 
    'distance=1000 unit=METER'
) = 'TRUE';


-- GEOJSON
SELECT
    name,
    '{"type": "Feature", "geometry": {"type": "Point", "coordinates": [' ||
    TO_CHAR(p.coordination.SDO_POINT.X) || ', ' ||
    TO_CHAR(p.coordination.SDO_POINT.Y) || ']}}' AS geojson
FROM
    point_table p;



------------------------------------------------------------------

CREATE TABLE polygon_table (
    name VARCHAR2(100),
    shape SDO_GEOMETRY
);

INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES (
    'POLYGON_TABLE',
    'SHAPE',
    SDO_DIM_ARRAY(
        SDO_DIM_ELEMENT('X', -180, 180, 0.05),
        SDO_DIM_ELEMENT('Y', -90, 90, 0.05)
    ),
    8307
);

CREATE INDEX polygon_table_shape_idx ON polygon_table(shape) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

INSERT INTO polygon_table (name, shape)
VALUES (
  'Fokotany A',
  SDO_GEOMETRY(
    2003, -- GTYPE for 2D polygon
    8307, -- SRID (Spatial Reference System Identifier), assuming WGS 84 here
    NULL,
    SDO_ELEM_INFO_ARRAY(1,1003,1), -- Exterior polygon ring
    SDO_ORDINATE_ARRAY(47.5, -18.8, 47.6, -18.8, 47.5, -18.9) -- Coordinates of the rectangle
  )
);
INSERT INTO polygon_table (name, shape)
VALUES (
  'Fokotany B',
  SDO_GEOMETRY(
    2003, -- GTYPE for 2D polygon
    8307, -- SRID (Spatial Reference System Identifier), assuming WGS 84 here
    NULL,
    SDO_ELEM_INFO_ARRAY(1,1003,1), -- Exterior polygon ring
    SDO_ORDINATE_ARRAY(47.4, -18.7, 47.5, -18.7, 47.4, -18.8) -- Coordinates of the rectangle
  )
);

-- GeoJSON
SELECT
    p.name,
    LISTAGG('[' || x || ', ' || y || ']', ', ') WITHIN GROUP (ORDER BY ROWNUM) AS shape
FROM
    polygon_table p,
    TABLE(SDO_UTIL.GETVERTICES(p.shape))
GROUP BY
    p.name;
;

