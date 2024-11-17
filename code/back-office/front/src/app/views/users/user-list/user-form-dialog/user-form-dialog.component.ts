import { Component, Inject } from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from '@angular/material/dialog';
import { NewUserRequest } from '../../../../dto/request/user/new-user.request';
import { Role } from '../../../../dto/role';
import { FormContainerComponent } from '../../../../components/form-container/form-container.component';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UserImportDialogComponent } from './user-import-dialog/user-import-dialog.component';
import { ImportUsersRequest } from '../../../../dto/request/user/import-users.request';

@Component({
  selector: 'app-add-new-user-dialog',
  templateUrl: './user-form-dialog.component.html',
  styleUrl: './user-form-dialog.component.scss',
})
export class UserFormDialogComponent extends FormContainerComponent {
  newUser: NewUserRequest = new NewUserRequest();

  displayImportProgressSpinner = false;

  constructor(
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<UserFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: {
      roles: Role[];
      userObjectHolder: NewUserRequest | null;
      onImportUsers: (request: ImportUsersRequest) => Promise<unknown>;
    }
  ) {
    const userForm = new FormGroup({
      identifier: new FormControl(
        data.userObjectHolder ? data.userObjectHolder.identifier : '',
        [Validators.required]
      ),
      roleId: new FormControl(
        data.userObjectHolder ? data.userObjectHolder.roleId : '',
        [Validators.required]
      ),
      name: new FormControl(
        data.userObjectHolder ? data.userObjectHolder.name : '',
        [Validators.required]
      ),
      firstName: new FormControl(
        data.userObjectHolder ? data.userObjectHolder.firstName : '',
        [Validators.required]
      ),
      contact: new FormControl(
        data.userObjectHolder ? data.userObjectHolder.contact : '',
        [
          Validators.required,
          Validators.pattern(/^03[0-9]{1}[0-9]{2}[0-9]{3}[0-9]{2}$/),
        ]
      ),
      password: new FormControl(
        data.userObjectHolder ? data.userObjectHolder.password : '',
        [Validators.required]
      ),
    });
    super(userForm);
  }

  override onConfirm(): void {
    const newUser = new NewUserRequest();
    newUser.identifier = this.componentForm.value.identifier;
    newUser.contact = this.componentForm.value.contact;
    newUser.name = this.componentForm.value.name;
    newUser.firstName = this.componentForm.value.firstName;
    newUser.password = this.componentForm.value.password;
    newUser.roleId = this.componentForm.value.roleId;
    this.dialogRef.close(newUser);
  }

  openImportDialog() {
    const dialogRef = this.dialog.open(UserImportDialogComponent, {
      data: { roles: this.data.roles },
    });

    dialogRef
      .afterClosed()
      .subscribe(async (importUsersRequest: ImportUsersRequest) => {
        if (importUsersRequest) {
          this.displayImportProgressSpinner = true;
          await this.data.onImportUsers(importUsersRequest);
          this.displayImportProgressSpinner = false;
        }
      });
  }
}
