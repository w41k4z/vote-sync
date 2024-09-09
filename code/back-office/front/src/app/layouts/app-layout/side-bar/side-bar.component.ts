import { Component } from '@angular/core';
import { Paths } from '../../../paths';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.scss',
})
export class SideBarComponent {
  menuItems = [
    {
      title: 'Accueil',
      children: [
        {
          path: Paths.DASHBOARD,
          icon: 'bi bi-house',
          title: 'Tableau de bord',
        },
      ],
    },
    {
      title: 'Permission',
      children: [
        { path: Paths.USERS, icon: 'bi bi-people', title: 'Utilisateurs' },
      ],
    },
  ];

  allItems() {
    let items: {
      pathTo: string | null;
      title: string | null;
      isMenuTitle: boolean;
      icon: string | null;
      content: string | null;
    }[] = [];
    this.menuItems.forEach((item) => {
      items.push({
        pathTo: null,
        title: item.title,
        isMenuTitle: true,
        icon: null,
        content: null,
      });
      for (let each of item.children) {
        items.push({
          pathTo: each.path,
          title: null,
          isMenuTitle: false,
          icon: each.icon,
          content: each.title,
        });
      }
    });
    return items;
  }
}
