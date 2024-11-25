import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LegislativeElectionResultService } from './legislative-election-result.service';
import { ElectoralResultPayload } from '../../../../dto/response/election/result/electoral-result-payload.response';
import { Endpoints } from '../../../../endpoints';

@Injectable({
  providedIn: 'root',
})
export class PresidentialElectionResultService extends LegislativeElectionResultService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getRegionalResults(electionId: string) {
    let strParam = `?electionId=${electionId}`;
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/presidential/regional${strParam}`
      )
    ).payload;
  }

  async getRegionalResultsPdf(electionId: string) {
    let strParam = `?electionId=${electionId}`;
    this.downloadCall(
      `${Endpoints.ELECTION_RESULTS}/exportation/presidential/regional${strParam}`,
      'Resultats.pdf'
    );
  }

  async getProvincialResults(electionId: string) {
    let strParam = `?electionId=${electionId}`;
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/presidential/provincial${strParam}`
      )
    ).payload;
  }

  async getProvincialResultsPdf(electionId: string) {
    let strParam = `?electionId=${electionId}`;
    this.downloadCall(
      `${Endpoints.ELECTION_RESULTS}/exportation/presidential/provincial${strParam}`,
      'Resultats.pdf'
    );
  }

  async getGlobalResults(electionId: string) {
    let strParam = `?electionId=${electionId}`;
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/presidential/global${strParam}`
      )
    ).payload;
  }

  async getGlobalResultsPdf(electionId: string) {
    let strParam = `?electionId=${electionId}`;
    this.downloadCall(
      `${Endpoints.ELECTION_RESULTS}/exportation/presidential/global${strParam}`,
      'Resultats.pdf'
    );
  }
}
