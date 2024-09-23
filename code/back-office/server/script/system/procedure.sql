CREATE OR REPLACE PROCEDURE assign_operators_to_polling_stations AS
    -- Array to hold operator IDs
    TYPE operator_id_array_type IS TABLE OF NUMBER;
    operator_id_array operator_id_array_type;

    operator_count NUMBER;
    operator_index NUMBER := 0;
    current_operator_id NUMBER;
BEGIN
    SELECT id BULK COLLECT INTO operator_id_array FROM utilisateurs WHERE id_role = 1; -- operator id
    operator_count := operator_id_array.COUNT;

    FOR bv_rec IN (SELECT id FROM bv) LOOP
        current_operator_id := operator_id_array(operator_index + 1); -- Arrays are 1-based in PL/SQL
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