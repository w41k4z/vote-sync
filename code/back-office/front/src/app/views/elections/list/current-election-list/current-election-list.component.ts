import { Component, Input } from '@angular/core';
import { Election } from '../../../../dto/election';
import { ElectionType } from '../../../../dto/election-type';
import { MatDialog } from '@angular/material/dialog';
import { ElectionImportDialogComponent } from './election-import-dialog/election-import-dialog.component';

@Component({
  selector: 'app-current-election-list',
  templateUrl: './current-election-list.component.html',
  styleUrl: './current-election-list.component.scss',
})
export class CurrentElectionListComponent {
  @Input() currentElections!: Election[];
  @Input() openAddDialog!: () => void;
  @Input() openEditDialog!: (election: Election) => void;
  @Input() openDeleteDialog!: (election: Election) => void;
  @Input() onClotureElection!: (election: Election) => Promise<void>;
  @Input() onImportResults!: (importResultsRequest: {
    electionId: number;
    file: File;
    password: string;
  }) => Promise<void>;
  @Input() routePathByElectionType!: (type: ElectionType) => string;
  @Input() errorMessage: string | null = null;
  @Input() message: string | null = null;

  isCloturing = false;

  constructor(private dialog: MatDialog) {}

  clotureElection = async (election: Election) => {
    this.isCloturing = true;
    setTimeout(() => {
      this.onClotureElection(election);
      this.isCloturing = false;
    }, 2000);
  };

  openImportDialog(election: Election) {
    const dialogRef = this.dialog.open(ElectionImportDialogComponent, {
      data: { election: election },
    });

    dialogRef
      .afterClosed()
      .subscribe(
        (importResultsRequest: {
          electionId: number;
          file: File;
          password: string;
        }) => {
          if (importResultsRequest) {
            this.onImportResults(importResultsRequest);
          }
        }
      );
  }
}
