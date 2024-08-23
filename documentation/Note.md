# Base

- La table enregistrement_candidat a été denormalisée (Possibilité de changement de parti politique des candidats).
- La table voix_candidat utilise numero_candidat comme clé étrangère (Pour eviter les jointures inutiles).
- Suppression de la table electeur (en raison de l'existence de la base nationale).
- La table enregistrement_electeur a été denormalisée pour faciliter l'integration avec la base nationale.
