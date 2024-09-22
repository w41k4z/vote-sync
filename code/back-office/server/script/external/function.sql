CREATE TYPE registered_candidate AS OBJECT (
    id NUMBER, -- candidate id for future use
    id_enregistrement NUMBER, -- registration id for future use
    numero_candidat NUMBER,
    information_candidat VARCHAR2(100),
    entite_politique VARCHAR2(50),
    description_entite_politique VARCHAR2(100),
    chemin_photo VARCHAR2(150),
    date_enregistrement DATE
);
/
CREATE TYPE registered_candidates AS TABLE OF registered_candidate;
/
CREATE OR REPLACE FUNCTION get_registered_candidates(
    id_election_param IN NUMBER,
    id_bv_param IN NUMBER
) RETURN registered_candidates
AS
    type_election VARCHAR2(50);
    v_id_commune NUMBER;
    v_id_district NUMBER;
    result registered_candidates;
BEGIN
    -- Retrieving the election type
    SELECT te.nom
    INTO type_election
    FROM elections e
    JOIN type_elections te
        ON e.id_type_election = te.id
    WHERE e.id = id_election_param
    ;

    IF type_election = 'Presidentielle' THEN
        SELECT
            registered_candidate(
                cp.id_candidat,
                cp.id_enregistrement,
                cp.numero_candidat,
                cp.information_candidat,
                cp.entite_politique,
                cp.description_entite_politique,
                cp.chemin_photo,
                cp.date_enregistrement
            )
        BULK COLLECT INTO result
        FROM candidats_presidentiels cp
        WHERE cp.id_election = id_election_param
        ;
    
    ELSIF type_election = 'Legislative' THEN
        SELECT
            registered_candidate(
                cl.id_candidat,
                cl.id_enregistrement,
                cl.numero_candidat,
                cl.information_candidat,
                cl.entite_politique,
                cl.description_entite_politique,
                cl.chemin_photo,
                cl.date_enregistrement
            )
        BULK COLLECT INTO result
        FROM candidats_legislatifs cl
        JOIN communes cm
            ON cl.id_district = cm.id_district
        JOIN fokontany fk
            ON fk.id_commune = cm.id
        JOIN cv
            ON fk.id = cv.id_fokontany
        JOIN bv
            ON cv.id = bv.id_cv
        WHERE 
            cl.id_election = id_election_param AND
            bv.id = id_bv_param
        ;

    ELSIF type_election = 'Locale' THEN
        SELECT
            registered_candidate(
                cl.id_candidat,
                cl.id_enregistrement,
                cl.numero_candidat,
                cl.information_candidat,
                cl.entite_politique,
                cl.description_entite_politique,
                cl.chemin_photo,
                cl.date_enregistrement
            )
        BULK COLLECT INTO result
        FROM candidats_locaux cl
        JOIN fokontany fk
            ON cl.id_commune = fk.id_commune
        JOIN cv 
            ON fk.id = cv.id_fokontany
        JOIN bv
            ON cv.id = bv.id_cv
        WHERE 
            cl.id_election = id_election_param AND
            bv.id = id_bv_param
        ;

    ELSE
        RAISE_APPLICATION_ERROR(-20001, 'Unknown election type');
    END IF;

    RETURN result;
END;
/