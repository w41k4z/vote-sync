import { Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { UserListPayload } from '../../../dto/response/user/user-list-payload.response';
import { MatDialog } from '@angular/material/dialog';
import { UserFormDialogComponent } from './user-form-dialog/user-form-dialog.component';
import { NewUserRequest } from '../../../dto/request/new-user.request';
import { UserService } from '../../../services/api/user/user.service';
import { UserStat } from '../../../dto/user-stat';
import { User } from '../../../dto/user';
import { RoleService } from '../../../services/api/role/role.service';
import { Role } from '../../../dto/role';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.scss',
})
export class ListUsersComponent {
  @Input() loading$!: Observable<Boolean>;
  @Input() error$!: Observable<string | null>;
  @Input() message$!: Observable<string | null>;
  @Input() userService!: UserService;
  @Input() userListPayload!: UserListPayload | null;
  @Input() stats!: UserStat[];
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
      console.log(newUserRequest);
      if (newUserRequest) {
        this.userService.createUser(newUserRequest).then((payload) => {
          if (payload) {
            this.updateUserListAndStats(payload.user);
          }
        });
      }
    });
  }

  private updateUserListAndStats(newUser: User) {
    this.userListPayload?.users.content.push(newUser);
    for (let stat of this.stats) {
      if (stat.role === newUser.role.name) {
        stat.count++;
        break;
      }
    }
  }
}
