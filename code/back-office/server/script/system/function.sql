CREATE TYPE nearby_polling_station AS OBJECT (
    id NUMBER,
    code CHAR(12),
    id_cv NUMBER,
    nom VARCHAR2(50)
);
/
CREATE TYPE nearby_polling_stations AS TABLE OF nearby_polling_station;
/
-- x: 47.52381395300234, y: -18.87211724421599, range: 5000
CREATE OR REPLACE FUNCTION find_nearby_polling_stations(
    x IN NUMBER,
    y IN NUMBER,
    range IN NUMBER
) RETURN nearby_polling_stations
AS
    result nearby_polling_stations;
BEGIN
    SELECT 
        nearby_polling_station(bv.id,
        bv.code,
        bv.id_cv,
        bv.nom)
    BULK COLLECT INTO result
    FROM cv
    JOIN bv
        ON bv.id_cv = cv.id
    WHERE SDO_WITHIN_DISTANCE(
        localisation,
        SDO_GEOMETRY(2001, 4326, SDO_POINT_TYPE(x, y, NULL), NULL, NULL),
        'distance=' || range || ' unit=METER'
    ) = 'TRUE';
    
    RETURN result;
END;
/