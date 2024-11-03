import { Paths } from '../../../../paths';
import { Privileges } from '../../../../privileges';

export const menuItems = [
  {
    title: 'Page',
    permitted: Privileges.ALL,
    children: [
      {
        path: Paths.HOME,
        icon: 'bi bi-house',
        title: 'Accueil',
        permitted: Privileges.ALL,
      },
      {
        path: Paths.DASHBOARD,
        icon: 'bi bi-speedometer2',
        title: 'Tableau de bord',
        permitted: [Privileges.ADMIN],
      },
    ],
  },
  {
    title: 'Permission',
    permitted: [Privileges.ADMIN],
    children: [
      {
        path: Paths.USERS,
        icon: 'bi bi-people',
        title: 'Utilisateurs',
        permitted: [Privileges.ADMIN],
      },
    ],
  },
  {
    title: 'Parametrage',
    permitted: [Privileges.ADMIN],
    children: [
      {
        path: Paths.ELECTIONS,
        icon: 'bi bi-archive',
        title: 'Elections',
        permitted: [Privileges.ADMIN],
      },
      {
        path: Paths.POLLING_STATIONS,
        icon: 'bi bi-box-seam',
        title: 'Bureaux de vote',
        permitted: [Privileges.ADMIN],
      },
    ],
  },
  {
    title: 'Résultat',
    permitted: [Privileges.OPERATOR],
    children: [
      {
        path: Paths.RESULT_VALIDATIONS,
        icon: 'bi bi-file-earmark-break',
        title: 'Validation',
        permitted: [Privileges.OPERATOR],
      },
    ],
  },
];
