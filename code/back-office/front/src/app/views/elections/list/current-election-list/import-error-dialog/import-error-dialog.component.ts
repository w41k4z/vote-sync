import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-import-error-dialog',
  templateUrl: './import-error-dialog.component.html',
  styleUrl: './import-error-dialog.component.scss',
})
export class ImportErrorDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { payload: Record<string, any> }
  ) {}

  getPayloadValues(): any[] {
    return Object.values(this.data.payload);
  }
}
