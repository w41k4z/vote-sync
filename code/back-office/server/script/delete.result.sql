DELETE FROM bv_details_resultats_provisoires;
DELETE FROM bv_resultats_provisoires;
DELETE FROM fokontany_details_resultats_provisoires;
DELETE FROM fokontany_resultats_provisoires;
DELETE FROM communes_details_resultats_provisoires;
DELETE FROM communes_resultats_provisoires;
DELETE FROM municipalites_details_resultats_provisoires;
DELETE FROM municipalites_resultats_provisoires;
DELETE FROM districts_details_resultats_provisoires;
DELETE FROM districts_resultats_provisoires;
DELETE FROM regions_details_resultats_provisoires;
DELETE FROM regions_resultats_provisoires;
DELETE FROM provinces_details_resultats_provisoires;
DELETE FROM provinces_resultats_provisoires;
DELETE FROM details_resultats_provisoires;
DELETE FROM resultats_provisoires;

DROP TABLE bv_details_resultats_provisoires;
DROP TABLE bv_resultats_provisoires;
DROP TABLE fokontany_details_resultats_provisoires;
DROP TABLE fokontany_resultats_provisoires;
DROP TABLE communes_details_resultats_provisoires;
DROP TABLE communes_resultats_provisoires;
DROP TABLE municipalites_details_resultats_provisoires;
DROP TABLE municipalites_resultats_provisoires;
DROP TABLE districts_details_resultats_provisoires;
DROP TABLE districts_resultats_provisoires;
DROP TABLE regions_details_resultats_provisoires;
DROP TABLE regions_resultats_provisoires;
DROP TABLE provinces_details_resultats_provisoires;
DROP TABLE provinces_resultats_provisoires;
DROP TABLE details_resultats_provisoires;
DROP TABLE resultats_provisoires;

CREATE OR REPLACE PROCEDURE delete_election (
	election_id NUMBER
) AS
BEGIN
	DELETE FROM bv_details_resultats_provisoires bvdr
	WHERE EXISTS (
		SELECT 1
		FROM bv_resultats_provisoires bvr
		WHERE bvr.id = bvdr.id_resultat_provisoire
		AND bvr.id_election = election_id
	);
	DELETE FROM bv_resultats_provisoires WHERE id_election = election_id;

	DELETE FROM fokontany_details_resultats_provisoires fkdr
	WHERE EXISTS (
		SELECT 1
		FROM fokontany_resultats_provisoires fkr
		WHERE fkr.id = fkdr.id_resultat_provisoire
		AND fkr.id_election = election_id
	);
	DELETE FROM fokontany_resultats_provisoires WHERE id_election = election_id;
	
	DELETE FROM communes_details_resultats_provisoires cmdr
	WHERE EXISTS (
		SELECT 1
		FROM communes_resultats_provisoires cmr
		WHERE cmr.id = cmdr.id_resultat_provisoire
		AND cmr.id_election = election_id
	);
	DELETE FROM communes_resultats_provisoires WHERE id_election = election_id;
	
	DELETE FROM municipalites_details_resultats_provisoires mcdr
	WHERE EXISTS (
		SELECT 1
		FROM municipalites_resultats_provisoires mcr
		WHERE mcr.id = mcdr.id_resultat_provisoire
		AND mcr.id_election = election_id
	);
	DELETE FROM municipalites_resultats_provisoires WHERE id_election = election_id;
	
	DELETE FROM districts_details_resultats_provisoires ddr
	WHERE EXISTS (
		SELECT 1
		FROM districts_resultats_provisoires dr
		WHERE dr.id = ddr.id_resultat_provisoire
		AND dr.id_election = election_id
	);
	DELETE FROM districts_resultats_provisoires WHERE id_election = election_id;
	
	DELETE FROM regions_details_resultats_provisoires rdr
	WHERE EXISTS (
		SELECT 1
		FROM regions_resultats_provisoires rr
		WHERE rr.id = rdr.id_resultat_provisoire
		AND rr.id_election = election_id
	);
	DELETE FROM regions_resultats_provisoires WHERE id_election = election_id;

	DELETE FROM provinces_details_resultats_provisoires pdr
	WHERE EXISTS (
		SELECT 1
		FROM provinces_resultats_provisoires pr
		WHERE pr.id = pdr.id_resultat_provisoire
		AND pr.id_election = election_id
	);
	DELETE FROM provinces_resultats_provisoires WHERE id_election = election_id;
	
	DELETE FROM details_resultats_provisoires gdr
	WHERE EXISTS (
		SELECT 1
		FROM resultats_provisoires gr
		WHERE gr.id = gdr.id_resultat_provisoire
		AND gr.id_election = election_id
	);
	DELETE FROM resultats_provisoires WHERE id_election = election_id;
		
	DELETE code_qr WHERE contenu = '110511060101';
	
	DELETE FROM details_resultats dr 
	WHERE EXISTS (
		SELECT 1
		FROM resultats r
		WHERE r.id = dr.id_resultat
		AND r.id_election = election_id
	);
	DELETE FROM resultat_images ri 
	WHERE EXISTS (
		SELECT 1
		FROM resultats r
		WHERE r.id = ri.id_resultat
		AND r.id_election = election_id
	);
	DELETE FROM resultats WHERE id_election = election_id;

	DELETE FROM enregistrement_candidats WHERE id_election = election_id;
	DELETE FROM enregistrement_electeurs WHERE id_election = election_id;

	DELETE FROM alertes WHERE id_election = election_id;

	DELETE FROM elections WHERE id = election_id;

	COMMIT;
END;
/