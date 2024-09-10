import { Paths } from '../../../paths';
import { Privileges } from '../../../privileges';

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
];
