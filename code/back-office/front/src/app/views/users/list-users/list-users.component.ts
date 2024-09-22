import { Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { UserFormDialogComponent } from './user-form-dialog/user-form-dialog.component';
import { NewUserRequest } from '../../../dto/request/new-user.request';
import { RoleService } from '../../../services/api/role/role.service';
import { Role } from '../../../dto/role';
import { User } from '../../../dto/user';
import { Page } from '../../../dto/response/page';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.scss',
})
export class ListUsersComponent {
  @Input() loading$!: Observable<Boolean>;
  @Input() error$!: Observable<string | null>;
  @Input() message$!: Observable<string | null>;
  @Input() onCreateUser!: (request: NewUserRequest) => void;
  @Input() userLists!: User[];
  @Input() page!: Page | null;
  @Input() onFilter!: (
    filter: string | null,
    userTypeFilter: string,
    page: number
  ) => void;
  @Input() onNextPage!: () => void;
  @Input() onPreviousPage!: () => void;
  roles: Role[] = [];

  constructor(private dialog: MatDialog, private roleService: RoleService) {
    this.roleService.getAllRoles().then((payload) => {
      if (payload) {
        this.roles = payload.roles;
      }
    });
  }

  openAddNewUserDialog() {
    const dialogRef = this.dialog.open(UserFormDialogComponent, {
      data: { roles: this.roles },
    });
    dialogRef.afterClosed().subscribe((newUserRequest: NewUserRequest) => {
      if (newUserRequest) {
        this.onCreateUser(newUserRequest);
      }
    });
  }
}
