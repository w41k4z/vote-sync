import { Injectable } from '@angular/core';
import { ApiCallService } from '../../api-call';
import { HttpClient } from '@angular/common/http';
import { ElectoralResultPayload } from '../../../../dto/response/election/result/electoral-result-payload.response';
import { Endpoints } from '../../../../endpoints';

@Injectable({
  providedIn: 'root',
})
export class LocalElectionResultService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getMunicipalResults(page: number, size: number, electionId: string) {
    let param = `?electionId=${electionId}`;
    if (page) {
      param += `&page=${page}`;
    }
    if (size) {
      param += `&size=${size}`;
    }
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/local/municipal${param}`
      )
    ).payload;
  }

  async getFokontanyResults(page: number, size: number, electionId: string) {
    let param = `?electionId=${electionId}`;
    if (page) {
      param += `&page=${page}`;
    }
    if (size) {
      param += `&size=${size}`;
    }
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/local/fokontany${param}`
      )
    ).payload;
  }

  async getPollingStationResults(
    page: number,
    size: number,
    electionId: string
  ) {
    let param = `?electionId=${electionId}`;
    if (page) {
      param += `&page=${page}`;
    }
    if (size) {
      param += `&size=${size}`;
    }
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/local/polling-station${param}`
      )
    ).payload;
  }
}
