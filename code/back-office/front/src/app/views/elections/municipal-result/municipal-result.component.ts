import { Component } from '@angular/core';
import { Election } from '../../../dto/election';
import { ElectoralResult } from '../../../dto/electoral-result';
import { ActivatedRoute } from '@angular/router';
import { ElectionResultService } from '../../../services/api/election/election-result/election-result.service';
import { ElectionService } from '../../../services/api/election/election.service';

@Component({
  selector: 'app-municipal-result',
  templateUrl: './municipal-result.component.html',
  styleUrl: './municipal-result.component.scss',
})
export class MunicipalResultComponent {
  results: string[] = ['Par bureau de vote', 'Par fokontany', 'Par commune'];
  current = 2;
  election: Election | null = null;
  electoralResults: ElectoralResult[] = [];

  constructor(
    private route: ActivatedRoute,
    private electionService: ElectionService,
    private electionResultService: ElectionResultService
  ) {
    const electionId = this.route.snapshot.paramMap.get('electionId');
    if (electionId) {
      this.electionService.getElection(electionId).then((payload) => {
        if (payload) {
          this.election = payload.election;
          this.getElectoralResults();
        }
      });
    }
  }

  getElectoralResults() {
    const electionId = this.route.snapshot.paramMap.get('electionId');
    if (electionId) {
      switch (this.current) {
        case 2:
          this.electionResultService
            .getMunicipalResults(electionId)
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults;
              }
            });
          break;
        default:
          break;
      }
    }
  }
}
