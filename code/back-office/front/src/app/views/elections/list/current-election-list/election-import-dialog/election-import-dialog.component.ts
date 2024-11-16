import { Component, Inject } from '@angular/core';
import { FormContainerComponent } from '../../../../../components/form-container/form-container.component';
import { Election } from '../../../../../dto/election';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-election-import-dialog',
  templateUrl: './election-import-dialog.component.html',
  styleUrl: './election-import-dialog.component.scss',
})
export class ElectionImportDialogComponent extends FormContainerComponent {
  importedFile: File | null = null;

  constructor(
    public dialogRef: MatDialogRef<ElectionImportDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: {
      election: Election;
    }
  ) {
    const uploadForm = new FormGroup({
      file: new FormControl(null, [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
    super(uploadForm);
  }

  // Letting reactive form to manage form validation
  setImportedFile(event: any) {
    this.importedFile = event.target.files[0];
  }

  override onConfirm(): void {
    this.dialogRef.close({
      electionId: this.data.election.id,
      file: this.importedFile,
      password: this.componentForm.value.password,
    });
  }
}
