import { Component } from '@angular/core';
import { Election } from '../../dto/election';
import { Paths } from '../../paths';
import { ElectionType } from '../../dto/election-type';
import { MatDialog } from '@angular/material/dialog';
import { ElectionDialogComponent } from './election-dialog/election-dialog.component';
import { ConfigureElectionRequest } from '../../dto/request/configure-election-request';
import { UpdateElectionRequest } from '../../dto/request/update-election-request';
import { DeleteDialogComponent } from '../../components/delete-dialog/delete-dialog.component';
import { ActiveElectionService } from '../../services/api/election/active-election.service';
import { ElectionArchiveService } from '../../services/api/election/election-archive.service';
import { Page } from '../../dto/response/page';
import { Observable } from 'rxjs';
import { ElectionService } from '../../services/api/election/election.service';
import { CloseElectionDialogComponent } from './close-election-dialog/close-election-dialog.component';
import { ImportErrorDialogComponent } from './list/current-election-list/import-error-dialog/import-error-dialog.component';

@Component({
  selector: 'app-elections',
  templateUrl: './elections.component.html',
  styleUrl: './elections.component.scss',
})
export class ElectionsComponent {
  isCloturing = false;
  electionPath = Paths.ELECTIONS;

  currentElections: Election[] = [];
  currentElectionError$: Observable<string | null>;
  currentElectionMessage$: Observable<string | null>;

  electionHistoryLoading$: Observable<Boolean>;
  electionHistory: Election[] = [];
  electionHistoryPage: Page | null = null;

  displayProgressSpinner = false;

  constructor(
    private electionService: ElectionService,
    private activeElectionService: ActiveElectionService,
    private electionArchiveService: ElectionArchiveService,
    public dialog: MatDialog
  ) {
    this.currentElectionError$ = activeElectionService.error$;
    this.currentElectionMessage$ = activeElectionService.message$;
    this.activeElectionService.getCurrentElections().then((payload) => {
      if (payload) {
        this.currentElections = payload.elections;
      }
    });
    this.electionHistoryLoading$ = this.electionArchiveService.loading$;
    this.electionArchiveService.getElectionArchive().then((payload) => {
      if (payload) {
        this.electionHistory = payload.elections.content;
        this.electionHistoryPage = payload.elections.page;
      }
    });
  }

  routePathByElectionType = (type: ElectionType) => {
    switch (type.type) {
      case 'Présidentielle':
        return `${this.electionPath}/result/presidential`;
      case 'Législative':
        return `${this.electionPath}/result/legislative`;
      case 'Locale':
        return `${this.electionPath}/result/local`;
      default:
        throw new Error('Unknown election type');
    }
  };

  private async configureElection(request: ConfigureElectionRequest) {
    return this.activeElectionService
      .configureElection(request)
      .then((payload) => {
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
          this.activeElectionService
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
        this.activeElectionService.deleteElection(data.id).then(() => {
          this.currentElections = this.currentElections.filter(
            (each) => each.id != data.id
          );
        });
      }
    });
  };

  clotureElection = async (election: Election) => {
    const dialogRef = this.dialog.open(CloseElectionDialogComponent, {
      data: {
        id: election.id,
      },
    });
    dialogRef.afterClosed().subscribe(async (data: { id: number }) => {
      if (data) {
        this.displayProgressSpinner = true;
        try {
          await this.electionService.closeElection(election.id);
          this.currentElections = this.currentElections.filter(
            (currentElection) => currentElection.id !== election.id
          );
          election.endDate = new Date().toISOString().split('T')[0];
          this.electionHistory.unshift(election);
        } catch (error) {
          throw error;
        } finally {
          this.displayProgressSpinner = false;
        }
      }
    });
  };

  importResults = async (importResultRequest: {
    electionId: number;
    file: File;
    password: string;
  }) => {
    this.displayProgressSpinner = true;
    try {
      const payload = await this.activeElectionService.importElectoralResults(
        importResultRequest
      );
      if (Object.keys(payload).length !== 0) {
        this.dialog.open(ImportErrorDialogComponent, {
          data: {
            payload: payload,
          },
        });
      }
    } catch (error) {
      throw error;
    } finally {
      this.displayProgressSpinner = false;
    }
  };

  onArchiveNextPage = async () => {
    if (this.electionHistoryPage) {
      if (
        this.electionHistoryPage.number + 1 <
        this.electionHistoryPage.totalPages
      ) {
        this.electionArchiveService
          .getElectionArchive(this.electionHistoryPage.number + 1)
          .then((payload) => {
            if (payload) {
              this.electionHistory = payload.elections.content;
              this.electionHistoryPage = payload.elections.page;
            }
          });
      }
    }
  };

  onArchivePreviousPage = async () => {
    if (this.electionHistoryPage) {
      if (this.electionHistoryPage.number - 1 >= 0) {
        this.electionArchiveService
          .getElectionArchive(this.electionHistoryPage.number - 1)
          .then((payload) => {
            if (payload) {
              this.electionHistory = payload.elections.content;
              this.electionHistoryPage = payload.elections.page;
            }
          });
      }
    }
  };
}
