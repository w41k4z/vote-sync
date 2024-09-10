import { Component } from '@angular/core';
import { Paths } from '../../../paths';
import { menuItems } from './items';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.scss',
})
export class SideBarComponent {
  items = menuItems;

  constructor(private authService: AuthService) {}

  allItems() {
    let items: {
      pathTo: string | null;
      title: string | null;
      isMenuTitle: boolean;
      icon: string | null;
      content: string | null;
    }[] = [];
    const userPrivilege = this.authService.getUserPrivilege();
    if (userPrivilege) {
      this.items.forEach((item) => {
        if (item.permitted.includes(userPrivilege)) {
          items.push({
            pathTo: null,
            title: item.title,
            isMenuTitle: true,
            icon: null,
            content: null,
          });
        }
        for (let each of item.children) {
          if (each.permitted.includes(userPrivilege)) {
            items.push({
              pathTo: each.path,
              title: null,
              isMenuTitle: false,
              icon: each.icon,
              content: each.title,
            });
          }
        }
      });
    }
    return items;
  }
}
