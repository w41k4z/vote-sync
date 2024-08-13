## Recap

#### Back-office

- Trois profils: admin, responsable et operateur de saisie.

  - admin:

    - acces au tableau de bord (presenter sous forme de map):
      - stat electorale (taux de participation...)
      - stat des alertes (nombre d'alerte par type, taux de resolution, ...)
      - stat des severités des alertes (nombre d'alerte par severité)
      - cartographie des incidents (Nombre d'incident par region)
      - performance des bv (basé sur la regularité des données et le temps de traitement des votes)
    - gestion des irrégularités (notification/alerte):
      - doublon
      - valeur extreme (taux de participation inhabituel)
      - pattern temporel (enregistrement de vote en dehors des heures d'ouverture)
      - cohérence des données (incohérence entre le nombre de vote et le nombre d'electeur)
    - CRUD (role et utilisateur, type election, candidat, district, bv, fk...)

  - responsable:

    - pointage electeur (par bv)
    - scan document resultat

  - operateur:

    - enregistrement electeur (par election, par bv)
    - verification/validation resultat importé

- Mobile

  - Login responsable
  - Enregistrement electeur (+indication temporelle)
  - Liste des enregistrés/non-enregistrés (filtre...)
  - Scan image document resultat
