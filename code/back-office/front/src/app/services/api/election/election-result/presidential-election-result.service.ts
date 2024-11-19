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

  async getProvincialResults(electionId: string) {
    let strParam = `?electionId=${electionId}`;
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/presidential/provincial${strParam}`
      )
    ).payload;
  }

  async getGlobalResults(electionId: string) {
    let strParam = `?electionId=${electionId}`;
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/presidential/global${strParam}`
      )
    ).payload;
  }
}
