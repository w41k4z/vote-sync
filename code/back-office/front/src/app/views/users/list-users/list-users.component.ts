import { Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { UserFormDialogComponent } from './user-form-dialog/user-form-dialog.component';
import { NewUserRequest } from '../../../dto/request/new-user.request';
import { RoleService } from '../../../services/api/role/role.service';
import { Role } from '../../../dto/role';
import { User } from '../../../dto/user';
import { Page } from '../../../dto/response/page';
import { DeleteDialogComponent } from '../../../components/delete-dialog/delete-dialog.component';
import { ImportUsersRequest } from '../../../dto/request/import-users.request';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.scss',
})
export class ListUsersComponent {
  @Input() loading$!: Observable<Boolean>;
  @Input() error$!: Observable<string | null>;
  @Input() message$!: Observable<string | null>;
  @Input() userLists!: User[];
  @Input() page!: Page | null;
  @Input() pageSize!: number;
  @Input() onCreateUser!: (request: NewUserRequest) => Promise<void>;
  @Input() onUpdateUser!: (
    userIndex: number,
    request: NewUserRequest
  ) => Promise<void>;
  @Input() onDeleteUser!: (userId: number) => Promise<unknown>;
  @Input() onImportUsers!: (request: ImportUsersRequest) => Promise<unknown>;
  @Input() onFilter!: (
    filter: string | null,
    userTypeFilter: string,
    page: number
  ) => void;
  @Input() onNextPage!: () => void;
  @Input() onPreviousPage!: () => void;
  userObjectHolder: NewUserRequest | null = null;
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
      data: {
        roles: this.roles,
        userObjectHolder: this.userObjectHolder,
        onImportUsers: this.onImportUsers,
      },
    });
    dialogRef.afterClosed().subscribe((newUserRequest: NewUserRequest) => {
      if (newUserRequest) {
        this.onCreateUser(newUserRequest)
          .then(() => {
            this.userObjectHolder = null;
          })
          .catch(() => {
            this.userObjectHolder = newUserRequest;
          });
      }
    });
  }

  openEditUserDialog(userIndex: number) {
    this.userObjectHolder = new NewUserRequest();
    this.userObjectHolder.name = this.userLists[userIndex].name;
    this.userObjectHolder.firstName = this.userLists[userIndex].firstName;
    this.userObjectHolder.roleId = this.userLists[userIndex].role.id;
    this.userObjectHolder.contact = this.userLists[userIndex].identifier;
    this.userObjectHolder.identifier = this.userLists[userIndex].identifier;
    this.userObjectHolder.password = this.userLists[userIndex].identifier;
    const dialogRef = this.dialog.open(UserFormDialogComponent, {
      data: {
        roles: this.roles,
        userObjectHolder: this.userObjectHolder,
        onImportUsers: this.onImportUsers,
      },
    });
    dialogRef.afterClosed().subscribe((newUserRequest: NewUserRequest) => {
      if (newUserRequest) {
        this.onUpdateUser(userIndex, newUserRequest);
        this.userObjectHolder = null;
      } else {
        this.userObjectHolder = null;
      }
    });
  }

  openDeleteDialog(userIndex: number) {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: {
        id: userIndex,
      },
    });
    dialogRef.afterClosed().subscribe((data: { id: number }) => {
      if (data) {
        console.log(data.id);
        this.onDeleteUser(this.userLists[data.id].id).then(() => {
          this.userLists.splice(data.id, 1);
        });
      }
    });
  }
}
