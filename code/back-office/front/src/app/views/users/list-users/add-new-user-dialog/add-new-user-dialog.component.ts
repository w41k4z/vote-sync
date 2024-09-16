import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { NewUserRequest } from '../../../../dto/request/new-user.request';

@Component({
  selector: 'app-add-new-user-dialog',
  templateUrl: './add-new-user-dialog.component.html',
  styleUrl: './add-new-user-dialog.component.scss',
})
export class AddNewUserDialogComponent {
  newUser = new NewUserRequest();

  constructor(public dialogRef: MatDialogRef<AddNewUserDialogComponent>) {}

  confirm(): void {
    this.dialogRef.close();
    console.log(this.newUser);
  }
}
