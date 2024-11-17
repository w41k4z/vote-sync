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


CREATE OR REPLACE PROCEDURE import_electoral_results (
    election_id NUMBER
) AS
BEGIN
    INSERT INTO resultats(id_election, id_bv, inscrits, homme_moins_36, femme_moins_36, homme_36_plus, femme_36_plus, handicapes, malvoyants, blancs, nuls, etat, importe)
    (
        SELECT
            election_id,
            bv.id,
            rsi.inscrits,
            rsi.homme_moins_36,
            rsi.femme_moins_36,
            rsi.homme_36_plus,
            rsi.femme_36_plus,
            rsi.handicapes,
            rsi.malvoyants,
            rsi.blancs,
            rsi.nuls,
            0,
            1
        FROM resultats_importes rsi
        JOIN bv
            ON rsi.code_bv = bv.code
        WHERE rsi.id_election = election_id
    );

    import_electoral_result_details(election_id);
    import_electoral_result_images;
    
END;
/

CREATE OR REPLACE PROCEDURE clear_result_imporatation_table (
    election_id NUMBER
) AS
BEGIN
    DELETE FROM resultats_importes WHERE id_election = election_id;
    DELETE FROM details_resultats_importes WHERE id_election = election_id;
    DELETE FROM resultat_images_importes WHERE id_election = election_id;
END;
/

CREATE OR REPLACE PROCEDURE import_electoral_result_details (
    election_id NUMBER
) AS
    type_election VARCHAR2(50);
BEGIN
    SELECT te.nom
    INTO type_election
    FROM elections e
    JOIN type_elections te
        ON e.id_type_election = te.id
    WHERE e.id = election_id
    ;

    IF type_election = 'Presidentielle' THEN
        import_presidential_result_details();
    ELSIF type_election = 'Legislative' THEN
        import_legislative_result_details();
    ELSIF type_election = 'Locale' THEN
        import_local_result_details();
    END IF;
END;
/
CREATE OR REPLACE PROCEDURE import_presidential_result_details AS
BEGIN
    MERGE INTO details_resultats dr
    USING (
        SELECT 
            rs.id AS id_resultat,
            ec.id AS id_enregistrement_candidat,
            drsi.voix
        FROM details_resultats_importes drsi
        JOIN bv
            ON drsi.code_bv = bv.code
        JOIN resultats rs
            ON drsi.id_election = rs.id_election
            AND bv.id = rs.id_bv
        JOIN enregistrement_candidats ec
            ON ec.id_election = rs.id_election
            AND ec.numero_candidat = drsi.numero_candidat
    ) source
    ON (dr.id_resultat = source.id_resultat AND dr.id_enregistrement_candidat = source.id_enregistrement_candidat)
    WHEN MATCHED THEN
        UPDATE SET dr.voix = source.voix
    WHEN NOT MATCHED THEN
        INSERT (id_resultat, id_enregistrement_candidat, voix)
        VALUES (source.id_resultat, source.id_enregistrement_candidat, source.voix);
END;
/
CREATE OR REPLACE PROCEDURE import_legislative_result_details AS
BEGIN
    MERGE INTO details_resultats dr
    USING (
        SELECT 
            rs.id AS id_resultat,
            ec.id AS id_enregistrement_candidat,
            drsi.voix
        FROM details_resultats_importes drsi
        JOIN bv
            ON drsi.code_bv = bv.code
        JOIN resultats rs
            ON drsi.id_election = rs.id_election
            AND bv.id = rs.id_bv
        JOIN cv
            ON bv.id_cv = cv.id
        JOIN fokontany fk
            ON cv.id_fokontany = fk.id
        JOIN communes cm
            ON fk.id_commune = cm.id
        JOIN districts ds
            ON cm.id_district = ds.id
        JOIN enregistrement_candidats ec
            ON ec.id_election = rs.id_election
            AND ec.id_district = ds.id
            AND ec.numero_candidat = drsi.numero_candidat
    ) source
    ON (dr.id_resultat = source.id_resultat AND dr.id_enregistrement_candidat = source.id_enregistrement_candidat)
    WHEN MATCHED THEN
        UPDATE SET dr.voix = source.voix
    WHEN NOT MATCHED THEN
        INSERT (id_resultat, id_enregistrement_candidat, voix)
        VALUES (source.id_resultat, source.id_enregistrement_candidat, source.voix);
END;
/
CREATE OR REPLACE PROCEDURE import_local_result_details AS
BEGIN
    MERGE INTO details_resultats dr
    USING (
        SELECT 
            rs.id AS id_resultat,
            ec.id AS id_enregistrement_candidat,
            drsi.voix
        FROM details_resultats_importes drsi
        JOIN bv
            ON drsi.code_bv = bv.code
        JOIN resultats rs
            ON drsi.id_election = rs.id_election
            AND bv.id = rs.id_bv
        JOIN cv
            ON bv.id_cv = cv.id
        JOIN fokontany fk
            ON cv.id_fokontany = fk.id
        JOIN communes cm
            ON fk.id_commune = cm.id
        JOIN municipalites mc
            ON cm.id_municipalite = mc.id
        JOIN enregistrement_candidats ec
            ON ec.id_election = rs.id_election
            AND ec.id_municipalite = mc.id
            AND ec.numero_candidat = drsi.numero_candidat
    ) source
    ON (dr.id_resultat = source.id_resultat AND dr.id_enregistrement_candidat = source.id_enregistrement_candidat)
    WHEN MATCHED THEN
        UPDATE SET dr.voix = source.voix
    WHEN NOT MATCHED THEN
        INSERT (id_resultat, id_enregistrement_candidat, voix)
        VALUES (source.id_resultat, source.id_enregistrement_candidat, source.voix);
END;
/

CREATE OR REPLACE PROCEDURE import_electoral_result_images AS
BEGIN
    INSERT INTO resultat_images (
        id_resultat,
        chemin_image
    ) (
        SELECT
            rs.id,
            rii.chemin_image
        FROM resultat_images_importes rii
        JOIN bv
            ON rii.code_bv = bv.code
        JOIN resultats rs
            ON rs.id_election = rii.id_election
            AND bv.id = rs.id_bv
    );
END;
/


-- Election results migration
CREATE OR REPLACE PROCEDURE close_election (
    election_id NUMBER
) AS
    type_election VARCHAR2(50);
BEGIN
    SELECT te.nom
    INTO type_election
    FROM elections e
    JOIN type_elections te
        ON e.id_type_election = te.id
    WHERE e.id = election_id
    ;

    -- all administrative division results are needed for statistic purpose
    migrate_polling_station_results(election_id);
    migrate_fokontany_results(election_id);
    migrate_communal_results(election_id);
    migrate_municipal_results(election_id);
    migrate_district_results(election_id);
    migrate_regional_results(election_id);
    migrate_provincial_results(election_id);
    migrate_national_results(election_id);

    UPDATE elections SET etat = 20, date_fin = SYSDATE WHERE id = election_id;
    
    DELETE FROM resultat_images rsi
    WHERE EXISTS (
        SELECT 1
        FROM resultats r
        WHERE r.id = rsi.id_resultat
        AND r.id_election = election_id
    );

    DELETE FROM details_resultats dr
    WHERE EXISTS (
        SELECT 1
        FROM resultats r
        WHERE r.id = dr.id_resultat
        AND r.id_election = election_id
    );
    
    DELETE FROM resultats WHERE id_election = election_id;
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;
/



CREATE OR REPLACE PROCEDURE migrate_polling_station_results (
    election_id NUMBER
) AS
BEGIN
    INSERT INTO bv_resultats_provisoires(
        id_election,
        id_bv,
        code_bv,
        nom_bv,
        id_fokontany,
        nom_fokontany,
        id_commune,
        nom_commune,
        id_municipalite,
        nom_municipalite,
        id_district_municipal,
        nom_district_municipal,
        id_district,
        nom_district,
        id_region,
        nom_region,
        inscrits,
        homme_moins_36,
        femme_moins_36,
        homme_36_plus,
        femme_36_plus,
        handicapes,
        malvoyants,
        blancs,
        nuls,
        exprimes,
        importe,
        nombre_alertes,
        nom_operateur,
        prenom_operateur,
        contact_operateur,
        nom_membre_bv,
        prenom_membre_bv,
        contact_membre_bv
    ) (
        SELECT
            bvrs.id_election,
            bvrs.id,
            bvrs.code,
            bvrs.nom,
            bvrs.id_fokontany,
            bvrs.nom_fokontany,
            bvrs.id_commune,
            bvrs.nom_commune,
            bvrs.id_municipalite,
            bvrs.nom_municipalite,
            bvrs.id_district_municipal,
            bvrs.nom_district_municipal,
            bvrs.id_district,
            bvrs.nom_district,
            bvrs.id_region,
            bvrs.nom_region,
            bvrs.inscrits,
            bvrs.homme_moins_36,
            bvrs.femme_moins_36,
            bvrs.homme_36_plus,
            bvrs.femme_36_plus,
            bvrs.handicapes,
            bvrs.malvoyants,
            bvrs.blancs,
            bvrs.nuls,
            bvrs.exprimes,
            bvrs.importe,
            bvrs.nombre_alertes,
            u.nom AS nom_operateur,
            u.prenom AS prenom_operateur,
            u.contact AS contact_operateur,
            u2.nom AS nom_membre_bv,
            u2.prenom AS prenom_membre_bv,
            u2.contact AS contact_membre_bv
        FROM resultat_statistique_par_bv bvrs
        JOIN bv
            ON bvrs.id = bv.id
        LEFT JOIN utilisateurs u
            ON bv.id_operateur_validateur = u.id
        LEFT JOIN utilisateurs u2
            ON bv.code = u2.identifiant
        WHERE id_election = election_id
    );

    INSERT INTO bv_details_resultats_provisoires(
        id_resultat_provisoire,
        numero_candidat,
        information_candidat,
        nom_entite_politique,
        description_entite_politique,
        voix_candidat,
        chemin_photo_candidat
    ) (
        SELECT
            brsp.id,
            rsbv.numero_candidat,
            rsbv.information_candidat,
            rsbv.nom_entite_politique,
            rsbv.description_entite_politique,
            rsbv.voix_candidat,
            rsbv.chemin_photo_candidat
        FROM resultats_par_bv rsbv
        JOIN bv_resultats_provisoires brsp
            ON rsbv.id_bv = brsp.id_bv
            AND rsbv.id_election = brsp.id_election
    );
END;
/

CREATE OR REPLACE PROCEDURE migrate_fokontany_results (
    election_id NUMBER
) AS
BEGIN
    INSERT INTO fokontany_resultats_provisoires(
        id_election,
        id_fokontany,
        code_fokontany,
        nom_fokontany,
        id_commune,
        nom_commune,
        id_municipalite,
        nom_municipalite,
        id_district_municipal,
        nom_district_municipal,
        id_district,
        nom_district,
        id_region,
        nom_region,
        inscrits,
        homme_moins_36,
        femme_moins_36,
        homme_36_plus,
        femme_36_plus,
        handicapes,
        malvoyants,
        blancs,
        nuls,
        exprimes,
        importes,
        nombre_bv,
        nombre_total_bv,
        nombre_alertes
    ) (
        SELECT
            frs.id_election,
            frs.id,
            frs.code,
            frs.nom,
            frs.id_commune,
            frs.nom_commune,
            frs.id_municipalite,
            frs.nom_municipalite,
            frs.id_district_municipal,
            frs.nom_district_municipal,
            frs.id_district,
            frs.nom_district,
            frs.id_region,
            frs.nom_region,
            frs.inscrits,
            frs.homme_moins_36,
            frs.femme_moins_36,
            frs.homme_36_plus,
            frs.femme_36_plus,
            frs.handicapes,
            frs.malvoyants,
            frs.blancs,
            frs.nuls,
            frs.exprimes,
            frs.importes,
            frs.nombre_bv,
            frs.nombre_total_bv,
            frs.nombre_alertes
        FROM resultat_statistique_par_fokontany frs
        WHERE id_election = election_id
    );

    INSERT INTO fokontany_details_resultats_provisoires(
        id_resultat_provisoire,
        numero_candidat,
        information_candidat,
        nom_entite_politique,
        description_entite_politique,
        voix_candidat,
        chemin_photo_candidat
    ) (
        SELECT
            frsp.id,
            rsf.numero_candidat,
            rsf.information_candidat,
            rsf.nom_entite_politique,
            rsf.description_entite_politique,
            rsf.voix_candidat,
            rsf.chemin_photo_candidat
        FROM resultats_par_fokontany rsf
        JOIN fokontany_resultats_provisoires frsp
            ON rsf.id_fokontany = frsp.id_fokontany
            AND rsf.id_election = frsp.id_election
    );
END;
/

-- presidential and legislative election only
CREATE OR REPLACE PROCEDURE migrate_communal_results (
    election_id NUMBER
) AS
BEGIN
    INSERT INTO communes_resultats_provisoires(
        id_election,
        id_commune,
        code_commune,
        nom_commune,
        id_district,
        nom_district,
        id_region,
        nom_region,
        inscrits,
        homme_moins_36,
        femme_moins_36,
        homme_36_plus,
        femme_36_plus,
        handicapes,
        malvoyants,
        blancs,
        nuls,
        exprimes,
        importes,
        nombre_bv,
        nombre_total_bv,
        nombre_alertes
    ) (
        SELECT
            crs.id_election,
            crs.id,
            crs.code,
            crs.nom,
            crs.id_district,
            crs.nom_district,
            crs.id_region,
            crs.nom_region,
            crs.inscrits,
            crs.homme_moins_36,
            crs.femme_moins_36,
            crs.homme_36_plus,
            crs.femme_36_plus,
            crs.handicapes,
            crs.malvoyants,
            crs.blancs,
            crs.nuls,
            crs.exprimes,
            crs.importes,
            crs.nombre_bv,
            crs.nombre_total_bv,
            crs.nombre_alertes
        FROM resultat_statistique_par_commune crs
        WHERE id_election = election_id
    );

    INSERT INTO communes_details_resultats_provisoires(
        id_resultat_provisoire,
        numero_candidat,
        information_candidat,
        nom_entite_politique,
        description_entite_politique,
        voix_candidat,
        chemin_photo_candidat
    ) (
        SELECT
            crsp.id,
            rsc.numero_candidat,
            rsc.information_candidat,
            rsc.nom_entite_politique,
            rsc.description_entite_politique,
            rsc.voix_candidat,
            rsc.chemin_photo_candidat
        FROM resultats_par_commune rsc
        JOIN communes_resultats_provisoires crsp
            ON rsc.id_commune = crsp.id_commune
            AND rsc.id_election = crsp.id_election
    );
END;
/

-- local election only
CREATE OR REPLACE PROCEDURE migrate_municipal_results (
    election_id NUMBER
) AS
BEGIN
    INSERT INTO municipalites_resultats_provisoires(
        id_election,
        id_municipalite,
        code_municipalite,
        nom_municipalite,
        id_district,
        nom_district,
        id_region,
        nom_region,
        inscrits,
        homme_moins_36,
        femme_moins_36,
        homme_36_plus,
        femme_36_plus,
        handicapes,
        malvoyants,
        blancs,
        nuls,
        exprimes,
        importes,
        nombre_bv,
        nombre_total_bv,
        nombre_alertes
    ) (
        SELECT
            mrs.id_election,
            mrs.id,
            mrs.code,
            mrs.nom,
            mrs.id_district,
            mrs.nom_district,
            mrs.id_region,
            mrs.nom_region,
            mrs.inscrits,
            mrs.homme_moins_36,
            mrs.femme_moins_36,
            mrs.homme_36_plus,
            mrs.femme_36_plus,
            mrs.handicapes,
            mrs.malvoyants,
            mrs.blancs,
            mrs.nuls,
            mrs.exprimes,
            mrs.importes,
            mrs.nombre_bv,
            mrs.nombre_total_bv,
            mrs.nombre_alertes
        FROM resultat_statistique_par_municipalite mrs
        WHERE id_election = election_id
    );

    INSERT INTO municipalites_details_resultats_provisoires(
        id_resultat_provisoire,
        numero_candidat,
        information_candidat,
        nom_entite_politique,
        description_entite_politique,
        voix_candidat,
        chemin_photo_candidat
    ) (
        SELECT
            crsp.id,
            rsm.numero_candidat,
            rsm.information_candidat,
            rsm.nom_entite_politique,
            rsm.description_entite_politique,
            rsm.voix_candidat,
            rsm.chemin_photo_candidat
        FROM resultats_par_municipalite rsm
        JOIN municipalites_resultats_provisoires crsp
            ON rsm.id_municipalite = crsp.id_municipalite
            AND rsm.id_election = crsp.id_election
    );
END;
/

-- presidential and legislative election only
CREATE OR REPLACE PROCEDURE migrate_district_results (
    election_id NUMBER
) AS
BEGIN
    INSERT INTO districts_resultats_provisoires(
        id_election,
        id_district,
        code_district,
        nom_district,
        id_region,
        nom_region,
        inscrits,
        homme_moins_36,
        femme_moins_36,
        homme_36_plus,
        femme_36_plus,
        handicapes,
        malvoyants,
        blancs,
        nuls,
        exprimes,
        importes,
        nombre_bv,
        nombre_total_bv,
        nombre_alertes
    ) (
        SELECT
            drs.id_election,
            drs.id,
            drs.code,
            drs.nom,
            drs.id_region,
            drs.nom_region,
            drs.inscrits,
            drs.homme_moins_36,
            drs.femme_moins_36,
            drs.homme_36_plus,
            drs.femme_36_plus,
            drs.handicapes,
            drs.malvoyants,
            drs.blancs,
            drs.nuls,
            drs.exprimes,
            drs.importes,
            drs.nombre_bv,
            drs.nombre_total_bv,
            drs.nombre_alertes
        FROM resultat_statistique_par_district drs
        WHERE id_election = election_id
    );

    INSERT INTO districts_details_resultats_provisoires(
        id_resultat_provisoire,
        numero_candidat,
        information_candidat,
        nom_entite_politique,
        description_entite_politique,
        voix_candidat,
        chemin_photo_candidat
    ) (
        SELECT
            drsp.id,
            rsd.numero_candidat,
            rsd.information_candidat,
            rsd.nom_entite_politique,
            rsd.description_entite_politique,
            rsd.voix_candidat,
            rsd.chemin_photo_candidat
        FROM resultats_par_district rsd
        JOIN districts_resultats_provisoires drsp
            ON rsd.id_district = drsp.id_district
            AND rsd.id_election = drsp.id_election
    );
END;
/

-- presidential election only
CREATE OR REPLACE PROCEDURE migrate_regional_results (
    election_id NUMBER
) AS
BEGIN
    INSERT INTO regions_resultats_provisoires(
        id_election,
        id_region,
        code_region,
        nom_region,
        inscrits,
        homme_moins_36,
        femme_moins_36,
        homme_36_plus,
        femme_36_plus,
        handicapes,
        malvoyants,
        blancs,
        nuls,
        exprimes,
        importes,
        nombre_bv,
        nombre_total_bv,
        nombre_alertes
    ) (
        SELECT
            rrs.id_election,
            rrs.id,
            rrs.code,
            rrs.nom,
            rrs.inscrits,
            rrs.homme_moins_36,
            rrs.femme_moins_36,
            rrs.homme_36_plus,
            rrs.femme_36_plus,
            rrs.handicapes,
            rrs.malvoyants,
            rrs.blancs,
            rrs.nuls,
            rrs.exprimes,
            rrs.importes,
            rrs.nombre_bv,
            rrs.nombre_total_bv,
            rrs.nombre_alertes
        FROM resultat_statistique_par_region rrs
        WHERE id_election = election_id
    );

    INSERT INTO regions_details_resultats_provisoires(
        id_resultat_provisoire,
        numero_candidat,
        information_candidat,
        nom_entite_politique,
        description_entite_politique,
        voix_candidat,
        chemin_photo_candidat
    ) (
        SELECT
            rrsp.id,
            rsr.numero_candidat,
            rsr.information_candidat,
            rsr.nom_entite_politique,
            rsr.description_entite_politique,
            rsr.voix_candidat,
            rsr.chemin_photo_candidat
        FROM resultats_par_region rsr
        JOIN regions_resultats_provisoires rrsp
            ON rsr.id_region = rrsp.id_region
            AND rsr.id_election = rrsp.id_election
    );
END;
/

-- presindential election only
CREATE OR REPLACE PROCEDURE migrate_provincial_results (
    election_id NUMBER
) AS
BEGIN
    INSERT INTO provinces_resultats_provisoires(
        id_election,
        id_province,
        nom_province,
        inscrits,
        homme_moins_36,
        femme_moins_36,
        homme_36_plus,
        femme_36_plus,
        handicapes,
        malvoyants,
        blancs,
        nuls,
        exprimes,
        importes,
        nombre_bv,
        nombre_total_bv,
        nombre_alertes
    ) (
        SELECT
            prs.id_election,
            prs.id,
            prs.nom,
            prs.inscrits,
            prs.homme_moins_36,
            prs.femme_moins_36,
            prs.homme_36_plus,
            prs.femme_36_plus,
            prs.handicapes,
            prs.malvoyants,
            prs.blancs,
            prs.nuls,
            prs.exprimes,
            prs.importes,
            prs.nombre_bv,
            prs.nombre_total_bv,
            prs.nombre_alertes
        FROM resultat_statistique_par_province prs
        WHERE id_election = election_id
    );

    INSERT INTO provinces_details_resultats_provisoires(
        id_resultat_provisoire,
        numero_candidat,
        information_candidat,
        nom_entite_politique,
        description_entite_politique,
        voix_candidat,
        chemin_photo_candidat
    ) (
        SELECT
            prsp.id,
            rsp.numero_candidat,
            rsp.information_candidat,
            rsp.nom_entite_politique,
            rsp.description_entite_politique,
            rsp.voix_candidat,
            rsp.chemin_photo_candidat
        FROM resultats_par_province rsp
        JOIN provinces_resultats_provisoires prsp
            ON rsp.id_province = prsp.id_province
            AND rsp.id_election = prsp.id_election
    );
END;
/

-- presindential election only
CREATE OR REPLACE PROCEDURE migrate_national_results (
    election_id NUMBER
) AS
BEGIN
    INSERT INTO resultats_provisoires(
        id_election,
        inscrits,
        homme_moins_36,
        femme_moins_36,
        homme_36_plus,
        femme_36_plus,
        handicapes,
        malvoyants,
        blancs,
        nuls,
        exprimes,
        importes,
        nombre_bv,
        nombre_total_bv,
        nombre_alertes
    ) (
        SELECT
            grs.id_election,
            grs.inscrits,
            grs.homme_moins_36,
            grs.femme_moins_36,
            grs.homme_36_plus,
            grs.femme_36_plus,
            grs.handicapes,
            grs.malvoyants,
            grs.blancs,
            grs.nuls,
            grs.exprimes,
            grs.importes,
            grs.nombre_bv,
            grs.nombre_total_bv,
            grs.nombre_alertes
        FROM resultat_statistique_election grs
        WHERE id_election = election_id
    );

    INSERT INTO details_resultats_provisoires(
        id_resultat_provisoire,
        numero_candidat,
        information_candidat,
        nom_entite_politique,
        description_entite_politique,
        voix_candidat,
        chemin_photo_candidat
    ) (
        SELECT
            rsp.id,
            rse.numero_candidat,
            rse.information_candidat,
            rse.nom_entite_politique,
            rse.description_entite_politique,
            rse.voix_candidat,
            rse.chemin_photo_candidat
        FROM resultats_election rse
        JOIN resultats_provisoires rsp
            ON rse.id_election = rsp.id_election
    );
END;
/