CREATE OR REPLACE TRIGGER trg_update_user_search_column
BEFORE INSERT OR UPDATE ON utilisateurs
FOR EACH ROW
BEGIN
    :NEW.search_column := :NEW.nom || ' ' || :NEW.prenom || ' ' || :NEW.contact || ' ' || :NEW.identifiant;
END;
/