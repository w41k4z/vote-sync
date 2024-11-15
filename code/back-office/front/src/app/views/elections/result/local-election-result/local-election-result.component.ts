import { Component } from '@angular/core';
import { Election } from '../../../../dto/election';
import { ElectoralResult } from '../../../../dto/electoral-result';
import { ActivatedRoute } from '@angular/router';
import { LocalElectionResultService } from '../../../../services/api/election/election-result/local-election-result.service';
import { ElectionService } from '../../../../services/api/election/election.service';
import { Page } from '../../../../dto/response/page';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-local-election-result',
  templateUrl: './local-election-result.component.html',
  styleUrl: './local-election-result.component.scss',
})
export class LocalElectionResultComponent {
  loading$: Observable<Boolean>;
  error$: Observable<string | null>;
  message$: Observable<string | null>;
  results: string[] = ['Par bureau de vote', 'Par fokontany', 'Par commune'];
  current = 0;
  election: Election | null = null;
  electoralResults: ElectoralResult[] = [];
  page: Page | null = null;

  constructor(
    private route: ActivatedRoute,
    private electionService: ElectionService,
    private electionResultService: LocalElectionResultService
  ) {
    this.loading$ = electionResultService.loading$;
    this.error$ = electionResultService.error$;
    this.message$ = electionResultService.message$;
    const electionId = this.route.snapshot.paramMap.get('electionId');
    if (electionId) {
      this.electionService.getElection(electionId).then((payload) => {
        if (payload) {
          this.election = payload.election;
          this.filter();
        }
      });
    }
  }

  handleResultChange(index: number) {
    this.current = index;
    this.filter();
  }

  filter(page: number = 0) {
    this.getElectoralResults(page);
  }

  getElectoralResults(page: number) {
    const electionId = this.route.snapshot.paramMap.get('electionId');
    if (electionId) {
      switch (this.current) {
        case 0:
          this.electionResultService
            .getPollingStationResults(page, 1, electionId)
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults.content;
                this.page = payload.electoralResults.page;
              }
            });
          break;
        case 1:
          this.electionResultService
            .getFokontanyResults(page, 1, electionId)
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults.content;
                this.page = payload.electoralResults.page;
              }
            });
          break;
        case 2:
          this.electionResultService
            .getMunicipalResults(page, 1, electionId)
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults.content;
                this.page = payload.electoralResults.page;
              }
            });
          break;
        default:
          break;
      }
    }
  }

  nextPage = () => {
    if (this.page) {
      if (this.page.number + 1 < this.page.totalPages) {
        this.filter(this.page.number + 1);
      }
    }
  };

  previousPage = () => {
    if (this.page) {
      if (this.page.number - 1 >= 0) {
        this.filter(this.page.number - 1);
      }
    }
  };
}
