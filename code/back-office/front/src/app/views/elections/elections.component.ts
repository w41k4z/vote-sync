import { Component } from '@angular/core';
import { ElectionService } from '../../services/api/election/election.service';
import { Election } from '../../dto/election';
import { Paths } from '../../paths';
import { ElectionType } from '../../dto/election-type';
import { MatDialog } from '@angular/material/dialog';
import { ElectionDialogComponent } from './election-dialog/election-dialog.component';
import { ConfigureElectionRequest } from '../../dto/request/configure-election-request';
import { electionData } from './static-data';
import { UpdateElectionRequest } from '../../dto/request/update-election-request';
import { DeleteDialogComponent } from '../../components/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-elections',
  templateUrl: './elections.component.html',
  styleUrl: './elections.component.scss',
})
export class ElectionsComponent {
  isCloturing = false;
  electionPath = Paths.ELECTIONS;
  currentElections: Election[] = [];
  electionHistory: Election[] = electionData;

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

  routePathByElectionType = (type: ElectionType) => {
    switch (type.type) {
      case 'Presidentielle':
        return `${this.electionPath}/result/presidential`;
      case 'Legislative':
        return `${this.electionPath}/result/legislative`;
      case 'Locale':
        return `${this.electionPath}/result/local`;
      default:
        throw new Error('Unknown election type');
    }
  };

  private async configureElection(request: ConfigureElectionRequest) {
    return this.electionService.configureElection(request).then((payload) => {
      if (payload) {
        this.currentElections.push(payload.election);
      }
    });
  }

  openElectionConfigurationDialog = () => {
    const dialogRef = this.dialog.open(ElectionDialogComponent);
    dialogRef
      .afterClosed()
      .subscribe((confifureElectionRequest: ConfigureElectionRequest) => {
        if (confifureElectionRequest) {
          this.configureElection(confifureElectionRequest);
        }
      });
  };

  openElectionConfigurationEditDialog = (election: Election) => {
    const dialogRef = this.dialog.open(ElectionDialogComponent, {
      data: {
        electionName: election.name,
        electionTypeId: election.type.id,
        electionDate: election.startDate,
      },
    });
    dialogRef
      .afterClosed()
      .subscribe((configureElectionRequest: ConfigureElectionRequest) => {
        if (configureElectionRequest) {
          const updateElectionRequest = new UpdateElectionRequest();
          updateElectionRequest.id = election.id;
          updateElectionRequest.name = configureElectionRequest.name;
          updateElectionRequest.startDate = configureElectionRequest.startDate;
          this.electionService
            .updateElection(updateElectionRequest)
            .then(() => {
              election.name = updateElectionRequest.name;
              election.startDate = updateElectionRequest.startDate;
            });
        }
      });
  };

  openDeleteDialog = (election: Election) => {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: {
        id: election.id,
      },
    });
    dialogRef.afterClosed().subscribe((data: { id: number }) => {
      if (data) {
        this.electionService.deleteElection(data.id).then(() => {
          this.currentElections = this.currentElections.filter(
            (each) => each.id != data.id
          );
        });
      }
    });
  };

  clotureElection = async (election: Election) => {
    this.currentElections = this.currentElections.filter(
      (currentElection) => currentElection.id !== election.id
    );
    election.endDate = new Date().toISOString().split('T')[0];
    this.electionHistory.unshift(election);
  };

  importResults = async (importResultRequest: {
    electionId: number;
    file: File;
    password: string;
  }) => {
    this.electionService.importElectoralResults(importResultRequest);
  };
}
