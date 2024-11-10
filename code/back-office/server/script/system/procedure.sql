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
    INSERT INTO resultats(id_election, id_bv, inscrits, blancs, nuls, etat)
    (
        SELECT
            election_id,
            bv.id,
            rsi.inscrits,
            rsi.blancs,
            rsi.nuls,
            0
        FROM resultats_importes rsi
        JOIN bv
            ON rsi.code_bv = bv.code
        WHERE rsi.id_election = election_id
    );

    import_electoral_result_details();
    EXECUTE IMMEDIATE 'TRUNCATE TABLE resultats_importes';
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
    EXECUTE IMMEDIATE 'TRUNCATE TABLE details_resultats_importes';
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

    migrate_polling_station_results(election_id);
    migrate_fokontany_results(election_id);
    migrate_communal_results(election_id);
    IF type_election = 'Locale' THEN
        migrate_municipal_results(election_id);
    END IF;
    migrate_district_results(election_id);
    migrate_regional_results(election_id);
    migrate_provincial_results(election_id);
    migrate_national_results(election_id);

    UPDATE elections SET etat = 20 WHERE id = election_id;
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
        nom_fokontany,
        nom_commune,
        nom_district,
        nom_region,
        inscrits,
        blancs,
        nuls,
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
            bvrs.nom_fokontany,
            bvrs.nom_commune,
            bvrs.nom_district,
            bvrs.nom_region,
            bvrs.inscrits,
            bvrs.blancs,
            bvrs.nuls,
            u.nom AS nom_operateur,
            u.prenom AS prenom_operateur,
            u.contact AS contact_operateur,
            u2.nom AS nom_membre_bv,
            u2.prenom AS prenom_membre_bv,
            u2.contact AS contact_membre_bv
        FROM bv_resultats bvrs
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
        nom_commune,
        nom_district,
        nom_region,
        inscrits,
        blancs,
        nuls
    ) (
        SELECT
            frs.id_election,
            frs.id,
            frs.code,
            frs.nom,
            frs.nom_commune,
            frs.nom_district,
            frs.nom_region,
            frs.inscrits,
            frs.blancs,
            frs.nuls
        FROM fokontany_resultats frs
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
        nom_district,
        nom_region,
        inscrits,
        blancs,
        nuls
    ) (
        SELECT
            crs.id_election,
            crs.id,
            crs.code,
            crs.nom,
            crs.nom_district,
            crs.nom_region,
            crs.inscrits,
            crs.blancs,
            crs.nuls
        FROM communes_resultats crs
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
        nom_district,
        nom_region,
        inscrits,
        blancs,
        nuls
    ) (
        SELECT
            mrs.id_election,
            mrs.id,
            mrs.code,
            mrs.nom,
            mrs.nom_district,
            mrs.nom_region,
            mrs.inscrits,
            mrs.blancs,
            mrs.nuls
        FROM municipalites_resultats mrs
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
        nom_region,
        inscrits,
        blancs,
        nuls
    ) (
        SELECT
            drs.id_election,
            drs.id,
            drs.code,
            drs.nom,
            drs.nom_region,
            drs.inscrits,
            drs.blancs,
            drs.nuls
        FROM districts_resultats drs
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
        blancs,
        nuls
    ) (
        SELECT
            rrs.id_election,
            rrs.id,
            rrs.code,
            rrs.nom,
            rrs.inscrits,
            rrs.blancs,
            rrs.nuls
        FROM regions_resultats rrs
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
        blancs,
        nuls
    ) (
        SELECT
            prs.id_election,
            prs.id,
            prs.nom,
            prs.inscrits,
            prs.blancs,
            prs.nuls
        FROM provinces_resultats prs
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
        blancs,
        nuls
    ) (
        SELECT
            grs.id_election,
            grs.inscrits,
            grs.blancs,
            grs.nuls
        FROM global_resultats grs
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