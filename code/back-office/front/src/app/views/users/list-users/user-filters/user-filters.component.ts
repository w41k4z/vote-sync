import { Component, Input } from '@angular/core';
import { Role } from '../../../../dto/role';

@Component({
  selector: 'app-user-filters',
  templateUrl: './user-filters.component.html',
  styleUrl: './user-filters.component.scss',
})
export class UserFiltersComponent {
  @Input() roles!: Role[];
  filter: string | null = null;
  userTypeFilter: string = '*';
  @Input() onFilter!: (
    filter: string | null,
    userTypeFilter: string,
    page: number
  ) => void;

  filterUsers() {
    this.onFilter(this.filter, this.userTypeFilter, 0);
  }
}
