CREATE OR REPLACE PROCEDURE assign_operators_to_polling_stations AS
    TYPE operator_id_array_type IS TABLE OF NUMBER;
    operator_id_array operator_id_array_type;
    operator_count NUMBER;
    operator_index NUMBER := 0;
    current_operator_id NUMBER;
BEGIN
    SELECT id BULK COLLECT INTO operator_id_array FROM utilisateurs WHERE id_role = 1; -- operator id
    operator_count := operator_id_array.COUNT;

    FOR bv_rec IN (SELECT id FROM bv) LOOP
        current_operator_id := operator_id_array(operator_index + 1);
        UPDATE bv
        SET id_operateur_validateur = current_operator_id
        WHERE id = bv_rec.id;

        IF operator_index + 1 = operator_count THEN
            operator_index := 0;
        ELSE
            operator_index := operator_index + 1;
        END IF;
    END LOOP;
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE import_electoral_result_details AS
BEGIN
    INSERT INTO details_resultats(id_resultat, id_enregistrement_candidat, voix) (
        SELECT 
            rs.id,
            ec.id,
            drsi.voix
        FROM details_resultats_importes drsi
        JOIN resultats rs
            ON drsi.id_resultat = rs.id
        JOIN enregistrement_candidats ec
            ON ec.id_election = rs.id_election
            AND ec.id_candidat = drsi.id_candidat
    );
END;
/