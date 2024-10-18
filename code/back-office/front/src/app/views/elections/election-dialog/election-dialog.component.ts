import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-election-dialog',
  templateUrl: './election-dialog.component.html',
  styleUrl: './election-dialog.component.scss',
})
export class ElectionDialogComponent {
  constructor(public dialogRef: MatDialogRef<ElectionDialogComponent>) {}
}
