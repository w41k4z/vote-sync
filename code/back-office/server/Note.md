Clean Architecture Source:
https://github.com/vinimrs/spring-boot-clean-architecture/tree/master/common

## Conception base

- Candidat = representant d'une entité politique (x >= 1)

- Election de candidat et non d'une entité politique (partie politique ou coalition)
  => Contre exemple: Lors d'une élection legislative, une entité politique peut participer dans plusieurs districts. Enregistrer une entité politique au lieu d'un candidat nécessitera x enregistrement de l'entité pour y district.

- Table et non vue materialisée
  => Les vues matérialisées sont rattachées à leur table d'origine donc duplicaton des données et configuration de methode de rafraichissement problématique (Les données sont volumineuses, possibilité de supprimer les données de la table d'origine après leur migration vers les tables des données agrégés)
  => Finalement apres reflexion, la table est mieux appropriée pour les données agrégés car les données sont volumineuses et les vues matérialisées devront être rafraichies completement.

  A penser: Pour la table des données agregés, serait ils mieux de s'arreter sur les bv, et continuer avec des vues materialisées (fokontany, commune, district, region, province) ?

- Certaines données de table peuvent être supprimés (comme la table des résultats, utilisateurs...) et d'autre non (election, localisation, ...)

- Table municipalite:

  - Municipalite = commune sauf pour les 6 arrondissements de la capitale
  - Regroupement des communes en fonction de l'id_municipalite du table commune pour former la municipalite
  - Code municipalite = code commune du groupement => id_district_municipalite = id_district_commune du premier commune du groupement

- Pour les views de résultat, objectif des <division>-resultats = resultat-statistique-par-<division> + liaison object (ONE TO MANY) des détails view resultat-par-<division>

- Pour les resultats par bv et fokontany, deux views sont necessaires:
  - pour les elections legislatives et presidentiels (pas de suffixe)
  - pour les elections locales (suffixe: <view_name>-resultat-local)
