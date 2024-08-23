## Recap

- 1 materiel par bureau de vote (pour le checking et scan des documents)
  => Importante necessité pour la verification des patterns temporels ainsi que la cohérence des données (votants enregistrés vs votants apres le vote)
- deploiement de l'application back-office en reseau interne uniquement
- exposition API pour l'application mobile (authentification, liste inscrit [en cache apres auth], enregistrement electeur, envoie resultat scanné [+authentification avant envoie])

### Modules

- gestion des utilisateurs (role, ...)
- gestion des elections (type, candidat ...)
- control des bureaux de vote (region, district, commune, fokontany inclu)
- control des electeurs (pas de CRUD, acces à la base nationale)
- gestion des alertes (doublon, valeur extreme, pattern temporel, coherence des données)
- gestion des resultats (scan, validation, export)
- etat d'analyse et statistique (map dashboard, ...)

### Objectif

#### Back-office

- Quatre profils: admin, chef CID, responsable et operateur de saisie.

  - admin:

    - gestion des utilisateurs:
      - role
      - ...
    - acces au tableau de bord (presenter sous forme de map):
      - stat electorale (taux de participation...)
      - stat des alertes (nombre d'alerte par type, taux de resolution, ...)
      - stat des severités des alertes (nombre d'alerte par severité)
      - cartographie des incidents (Nombre d'incident par region)
      - performance des bv (basé sur la regularité des données et le temps de traitement des votes)
    - gestion des irrégularités (notification/alerte):
      - doublon (dès l'enregistrement)
      - valeur extreme (taux de participation inhabituel)
      - pattern temporel (enregistrement de vote en dehors des heures d'ouverture)
      - cohérence des données (incohérence entre le nombre de vote et le nombre d'electeur, votant enregistré vs votant apres le vote)
      - absence de resultat (ou votants = 0)
    - gestion élection (CRUD role et utilisateur, type election, candidat, district, bv, fk...)

  - responsable:

    - enregistrement electeur (par bv)

  - chef CID:

    - scan document resultat (authentifié mais pas forcement un reponsable...)

  - operateur:

    - verification/validation resultat importé

#### Mobile

- Login responsable
- Liste des inscrits/enregistrés/non-enregistrés (filtre...)
- Enregistrement electeur (+indication temporelle)
- Scan image document resultat

### Etapes

#### Pre-election

- Création de l'election en question par l'admin (candidat, responsable ...)
- Enregistrement des électeurs par les operateurs (par bureau de vote)

#### Election

- Pointage des électeurs par les responsables (par bureau de vote)
- Suivi des alertes par les administrateurs
- Scan des documents de resultat par les responsables
  => Pour le resultat, un formulaire de validation est proposé apres le scan. Puis lors de l'envoie, le fichier source sera uploadé sur un serveur dedié (ex: Firebase ou un serveur locale [transmission ftp]), les informations extraites + le lien genéré seront stockées dans la base de données pour une validation ultérieure.

  (Le choix d'un serveur en interne est encouragée pour plus de sécurité, un simple serveur apache est suffisante pour faciliter la communication avec le serveur de traitement [ecriture et lecture])

#### Post-election

- Validation des resultats par les operateurs (image vs données)
- Export des resultats (pdf)
