import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NewUserRequest } from '../../../../dto/request/new-user.request';
import { Role } from '../../../../dto/role';

@Component({
  selector: 'app-add-new-user-dialog',
  templateUrl: './user-form-dialog.component.html',
  styleUrl: './user-form-dialog.component.scss',
})
export class UserFormDialogComponent {
  newUser: NewUserRequest = new NewUserRequest();

  constructor(
    public dialogRef: MatDialogRef<UserFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: { roles: Role[]; userObjectHolder: NewUserRequest | null }
  ) {
    if (data.userObjectHolder) {
      this.newUser = data.userObjectHolder;
    }
  }

  confirm(): void {
    this.dialogRef.close(this.newUser);
  }
}
