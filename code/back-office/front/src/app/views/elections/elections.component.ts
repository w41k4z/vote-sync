import { Component } from '@angular/core';
import { ElectionService } from '../../services/api/election/election.service';
import { Election } from '../../dto/election';
import { Paths } from '../../paths';
import { ElectionType } from '../../dto/election-type';
import { MatDialog } from '@angular/material/dialog';
import { ElectionDialogComponent } from './election-dialog/election-dialog.component';
import { ConfigureElectionRequest } from '../../dto/request/configure-election-request';

@Component({
  selector: 'app-elections',
  templateUrl: './elections.component.html',
  styleUrl: './elections.component.scss',
})
export class ElectionsComponent {
  electionPath = Paths.ELECTIONS;
  currentElections: Election[] = [];
  electionHistory: Election[] = [];

  constructor(
    private electionService: ElectionService,
    public dialog: MatDialog
  ) {
    this.electionService.getCurrentElections().then((payload) => {
      if (payload) {
        this.currentElections = payload.elections;
      }
    });
  }

  getRoutePathByElectionType(type: ElectionType) {
    switch (type.type) {
      case 'Presidentielle':
        return `${this.electionPath}/result/presidential`;
      case 'Legislative':
        return `${this.electionPath}/result/legislative`;
      case 'Locale':
        return `${this.electionPath}/result/municipal`;
      default:
        throw new Error('Unknown election type');
    }
  }

  async configureElection(request: ConfigureElectionRequest) {
    return this.electionService.configureElection(request).then((payload) => {
      if (payload) {
        this.currentElections.push(payload.election);
      }
    });
  }

  openAddDialog() {
    const dialogRef = this.dialog.open(ElectionDialogComponent);
    dialogRef
      .afterClosed()
      .subscribe((confifureElectionRequest: ConfigureElectionRequest) => {
        if (confifureElectionRequest) {
          this.configureElection(confifureElectionRequest);
        }
      });
  }
}
