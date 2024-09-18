# Type d'election:

- Présidentielle:
  Niveau : National
  Responsabilité : Chef de l'État, politique nationale et internationale.

- Législative: renouvellement des membres de l'assemblée nationale (Antenimierampirenena) (composé de 163 députés)
  Niveau : National
  Responsabilité : Élaboration des lois, contrôle du gouvernement.
  Source: https://www.ceni-madagascar.mg/wp-content/uploads/2024/03/Ampliation-decret-n%C2%B02024-582-repartition-des-sieges-pour-les-elections-legislatives.pdf

- Communale: Maire...
  Niveau : Local (communal)
  Responsabilité : Gestion des affaires locales, services publics au niveau des communes.

# Stat:

- BV: 28.124 (29/05/2024) [https://www.ceni-madagascar.mg/wp-content/uploads/2024/05/LISTE-ET-EMPLKACEMENT-BV-ET-NOMBRES-DES-ELECTEURS-PAR-BV-DU-15-MAI-2024.pdf]

- FK:
  19.340 (01/08/2023) [https://www.ceni-madagascar.mg/wp-content/uploads/2023/08/NOMBRE-DES-ELECTEURS-PAR-FOKONTANY-DU-01-AOUT-2023.pdf]

  VS

  12.759 (29/05/2024) [https://www.ceni-madagascar.mg/wp-content/uploads/2024/05/LISTE-ET-EMPLKACEMENT-BV-ET-NOMBRES-DES-ELECTEURS-PAR-BV-DU-15-MAI-2024.pdf]
  
  VS
  
  17.459 (05/03/2021) pour les données spatiales recoltéés

- Commune:
  1.693 (01/04/2015) [https://fr.wikipedia.org/wiki/Commune_(Madagascar)#:~:text=Madagascar%20est%20divis%C3%A9e%20en%201,m%C3%A9thode%20du%20plus%20fort%20reste.]

  VS

  1.547 (29/05/2024) [https://www.ceni-madagascar.mg/wp-content/uploads/2024/05/LISTE-ET-EMPLKACEMENT-BV-ET-NOMBRES-DES-ELECTEURS-PAR-BV-DU-15-MAI-2024.pdf]
  
  VS
  
  1.578 (02/03/2021) pour les données spatiales recoltées

- District:
  120 (13/03/2024) [https://www.ceni-madagascar.mg/wp-content/uploads/2024/03/Ampliation-decret-n%C2%B02024-582-repartition-des-sieges-pour-les-elections-legislatives.pdf]
  
  VS
  
  114 (05/03/2021) pour les données spatiales recoltées

- Region: 
  24 (13/03/2024) [https://www.ceni-madagascar.mg/wp-content/uploads/2024/03/Ampliation-decret-n%C2%B02024-582-repartition-des-sieges-pour-les-elections-legislatives.pdf]
  
  VS
  
  22 (05/03/2021) pour les données spatiales recoltées

- Province: 06 (depuis toujours)

# Données spatiale:

- https://cartomad-ae-cirad.hub.arcgis.com/search?categories=%252Fcategories%252Funit%25C3%25A9s%2520administratives

# Analyse de l'existant:

## Post-election:

- Apres le comptage des votes, trois entités auront la responsabilité de valider les resultats:

  - La CENI (Commission Electorale Nationale Indépendante)
  - La HCC (Haute Cour Constitutionnelle)
  - Le Conseil d'Etat

- La CENI:

  - La CENI est chargée de la centralisation des resultats des bureaux de vote (BV) et de la publication des resultats provisoires.
  - La CENI est composée de 9 membres:
    - 1 président
    - 1 vice-président
    - 1 rapporteur
    - 6 membres
  - La CENI est chargée de:
    - L'organisation des élections
    - La publication des resultats provisoires
    - La transmission des resultats à la HCC
    - La publication des resultats definitifs

- La HCC:

  - La HCC est chargée de la validation des resultats des elections.
  - La HCC est chargée de:
    - La validation des resultats des elections
    - La publication des resultats definitifs
    - La proclamation des resultats definitifs

- Le Conseil d'Etat:
  ????

- La corruption n'est possible qu'au niveau des bureaux de vote.


# Nettoyage des données (Finalement, on va prendre les données d'Antananarivo)

Pour adapter les données spatiales avec les résultas:

- Les données spatiales
- Ajout d'un colonne id_groupement_region dans la table de résultat:
	- Fitovinany: 35
	- Vatovavy: 35 (23ème région)
	- Analanjirofo: 52
	- Ambatosoa: 52 (24ème région)

Note: J'ai abandonné de nettoyer les données à partir de commune en raison du volume de donnée.
Pour y remedier, j'ai generé les codes communes (code commune = code district + incremental two digits)