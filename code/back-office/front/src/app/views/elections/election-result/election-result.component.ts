import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ElectionService } from '../../../services/api/election/election.service';
import { Election } from '../../../dto/election';
import { ElectoralResult } from '../../../dto/electoral-result';
import { ElectionResultService } from '../../../services/api/election/election-result/election-result.service';

@Component({
  selector: 'app-election-result',
  templateUrl: './election-result.component.html',
  styleUrl: './election-result.component.scss',
})
export class ElectionResultComponent {
  results: string[] = ['Par bureau de vote', 'Par fokontany', 'Par municipale'];
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
                console.log(payload);
                console.log(this.electoralResults);
              }
            });
          break;
        default:
          break;
      }
    }
  }
}
