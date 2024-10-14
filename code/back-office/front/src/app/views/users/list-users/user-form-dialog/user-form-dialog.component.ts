import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NewUserRequest } from '../../../../dto/request/new-user.request';
import { Role } from '../../../../dto/role';
import { FormContainerComponent } from '../../../../components/form-container/form-container.component';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { first } from 'rxjs';

@Component({
  selector: 'app-add-new-user-dialog',
  templateUrl: './user-form-dialog.component.html',
  styleUrl: './user-form-dialog.component.scss',
})
export class UserFormDialogComponent extends FormContainerComponent {
  newUser: NewUserRequest = new NewUserRequest();

  constructor(
    public dialogRef: MatDialogRef<UserFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: { roles: Role[]; userObjectHolder: NewUserRequest | null }
  ) {
    const userForm = new FormGroup({
      identifier: new FormControl('', [Validators.required]),
      roleId: new FormControl('', [Validators.required]),
      name: new FormControl('', [Validators.required]),
      firstName: new FormControl('', [Validators.required]),
      contact: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
    super(userForm);
    if (data.userObjectHolder) {
      this.newUser = data.userObjectHolder;
    }
  }

  confirm(): void {
    this.submitting();
    if (this.componentForm.invalid) {
      return;
    }
    console.log(this.componentForm.value);
    // this.reset();
    // this.dialogRef.close(this.newUser);
  }
}
