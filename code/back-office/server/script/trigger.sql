CREATE OR REPLACE TRIGGER id_role_trigger
BEFORE INSERT ON roles
FOR EACH ROW
BEGIN
    SELECT role_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER id_utilisateur_trigger
BEFORE INSERT ON utilisateurs
FOR EACH ROW
BEGIN
    SELECT utilisateur_seq.NEXTVAL INTO :new.id FROM dual;
END;
/