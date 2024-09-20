Clean Architecture Source:
https://github.com/vinimrs/spring-boot-clean-architecture/tree/master/common

## Conception base

- Candidat = representant d'une entité politique (x >= 1)

- Election de candidat et non d'une entité politique (partie politique ou coalition)
  => Contre exemple: Lors d'une élection legislative, une entité politique peut participer dans plusieurs districts. Enregistrer une entité politique au lieu d'un candidat nécessitera x enregistrement de l'entité pour y district.
