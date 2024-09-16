- 09/08/2024

  - réalisation:
    - Plus de 28.000 BV au total (probleme de financement pour les installations nécessaires)
      => Limitation au niveau commune (1547 commune à peu près)
  - securité:
    - exposition de la base nationale par le biais des appels Web services.
      => mise en cache des données et filtrage des resultats par FK appellant (un seul `endpoint` en exposition)
  - login responsable:
    - localisation assigné ? BV ? Fokotany ? Commune ?
      => ???

- 16/08/2024

  - Version mobile à supporter:
    - les librairies doivent etre selectionnées specifiquement pour supporter les versions mobiles
      => Choix des librairies supportant de large gamme de version mobile
      (min: SDK 21, Android 5.0, iOS 9.0)

- 02/09/2024

  - Donnée spatiale obselète:
    => Adapter les données à jour en fonction des données spatiales recoltées (Groupement des BV du région Vatovavy dans Fitovinany pour reformer la région Vatovavy Fitovinany)

- 16/09/2024
  - Vue matérialisée vs table des résultats agrégés:
    => En connaissance du fait que le volume des données est important, il est préférable de créer des tables pour les résultats agrégés, et ainsi supprimer les données de la table d'origine pour plus de performance.
