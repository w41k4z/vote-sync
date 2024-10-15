import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Role } from '../../../../../dto/role';
import { FormContainerComponent } from '../../../../../components/form-container/form-container.component';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ImportUsersRequest } from '../../../../../dto/request/import-users.request';

@Component({
  selector: 'app-user-import-dialog',
  templateUrl: './user-import-dialog.component.html',
  styleUrl: './user-import-dialog.component.scss',
})
export class UserImportDialogComponent extends FormContainerComponent {
  importedFile: File | null = null;

  constructor(
    public dialogRef: MatDialogRef<UserImportDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: {
      roles: Role[];
    }
  ) {
    const uploadForm = new FormGroup({
      roleId: new FormControl(null, [Validators.required]),
      file: new FormControl(null, [Validators.required]),
    });
    super(uploadForm);
  }

  // Letting reactive form to manage form validation
  setImportedFile(event: any) {
    this.importedFile = event.target.files[0];
  }

  confirm(): void {
    this.submitting();
    if (this.componentForm.invalid || !this.importedFile) {
      return;
    }
    const importUsersRequest = new ImportUsersRequest();
    importUsersRequest.roleId = this.componentForm.value.roleId;
    importUsersRequest.file = this.importedFile;
    this.dialogRef.close(importUsersRequest);
  }
}
