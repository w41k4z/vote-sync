import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-close-election-dialog',
  templateUrl: './close-election-dialog.component.html',
  styleUrl: './close-election-dialog.component.scss',
})
export class CloseElectionDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<CloseElectionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: number }
  ) {}

  confirm(): void {
    this.dialogRef.close(this.data);
  }

  close(): void {
    this.dialogRef.close();
  }
}
