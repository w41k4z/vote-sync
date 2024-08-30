- Encadreur: Mr Mahery

(Proposition personnelle)

- Theme: Digitalisation electorale (Collecte des voix, Système de surveillance et d'alerte pour les irrégularités électorales)
  => Digitalisation du processus électoral : Développement d'une application mobile et back-office pour la gestion intégrée des élections populaires.

- Fonctionnalité:

  - Recolte de données:

    - Application mobile
    - Scan image => resultat

  - Détection d'anomalie:

    - Valeurs extrêmes
      Exemple : Dans une région avec une participation électorale habituelle autour de 60%, un bureau de vote rapporte une participation de 95%. Ce résultat est détecté comme anormalement élevé par l'algorithme.

    xx A étudier xx

    - Analyse des patterns temporels
      Exemple : Un pic soudain de votes est enregistré à 2h du matin, en dehors des heures d'ouverture des bureaux de vote. L'algorithme identifie cette activité comme suspecte.

    - Clustering et détection de l'outlier
      Exemple : La plupart des bureaux de vote d'une région montrent des résultats similaires, sauf un qui montre un taux de votes pour un candidat disproportionnellement élevé. L'algorithme de clustering identifie ce bureau comme un outlier.

    - Cohérence des données
      Exemple : Les résultats électoraux d'un bureau de vote montrent un total de 1 500 votes, mais les listes électorales ne comptent que 1 200 électeurs. L'incohérence est détectée.

    xx A étudier xx

    - Doubles votes
      Exemple : Un même identifiant électoral apparaît dans les listes de deux bureaux de vote différents. L'algorithme de matching détecte le possible double vote.

  - Alerte

    - Notification en temps reel
      Exemple : Lorsqu'une anomalie est détectée (comme un nombre de votes anormalement élevé), une notification est immédiatement envoyée aux administrateurs via un tableau de bord, un SMS, ou un email.

    - Niveaux de séverité
      Exemple : Les alertes peuvent être catégorisées par niveaux de sévérité (bas, moyen, élevé) selon la nature de l'anomalie détectée, permettant de prioriser les réponses.

    - Rapports d'incident et Suivi des alertes
      Exemple : Un rapport d'incident détaillant la nature de l'anomalie, l'emplacement, et le temps de détection est généré et peut être consulté par les responsables. Les alertes sont enregistrées dans un historique, permettant de suivre les résolutions et de documenter les réponses apportées.

- Détails

  - Profil: Responsable, opérateur de saisie

  - Responsable:

    - tableau de bord (stat electorale, ...)
    - validation des données
    - gestion des rapports/alertes

  - Opérateur de saise:

    - import/scan des données
    - gestion BDV

- Internship: from 2024-08-05 to 2024-11-18
