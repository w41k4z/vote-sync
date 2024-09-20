Clean Architecture Source:
https://github.com/vinimrs/spring-boot-clean-architecture/tree/master/common

## Conception base

- Candidat = representant d'une entité politique (x >= 1)

- Election de candidat et non d'une entité politique (partie politique ou coalition)
  => Contre exemple: Lors d'une élection legislative, une entité politique peut participer dans plusieurs districts. Enregistrer une entité politique au lieu d'un candidat nécessitera x enregistrement de l'entité pour y district.

- Table et non vue materialisée
  => Les vues matérialisées sont rattachées à leur table d'origine donc duplicaton des données et configuration de methode de rafraichissement problématique (Les données sont volumineuses, possibilité de supprimer les données de la table d'origine après leur migration vers les tables des données agrégés)

  A penser: Pour la table des données agregés, serait ils mieux de s'arreter sur les bv, et continuer avec des vues materialisées (fokontany, commune, district, region, province) ?
