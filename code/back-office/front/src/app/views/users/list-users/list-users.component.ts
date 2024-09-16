import { Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { UserListPayload } from '../../../dto/response/user/user-list-payload.response';
import { MatDialog } from '@angular/material/dialog';
import { AddNewUserDialogComponent } from './add-new-user-dialog/add-new-user-dialog.component';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.scss',
})
export class ListUsersComponent {
  @Input() loading$!: Observable<Boolean>;
  @Input() error$!: Observable<string | null>;
  @Input() message$!: Observable<string | null>;
  @Input() userListPayload!: UserListPayload | null;

  constructor(private dialog: MatDialog) {}

  openAddNewUserDialog() {
    const dialogRef = this.dialog.open(AddNewUserDialogComponent);
    dialogRef.afterClosed().subscribe();
  }
}
