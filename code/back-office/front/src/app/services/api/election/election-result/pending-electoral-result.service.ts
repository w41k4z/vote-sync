import { Injectable } from '@angular/core';
import { ApiCallService } from '../../api-call';
import { HttpClient } from '@angular/common/http';
import { PeendingElectoralResultPayload } from '../../../../dto/response/election/result/pending-electoral-result-payload.response';
import { Endpoints } from '../../../../endpoints';

@Injectable({
  providedIn: 'root',
})
export class PendingElectoralResultService extends ApiCallService {
  constructor(httpClient: HttpClient) {
    super(httpClient);
  }

  async getPendingElectoralResults(
    page: number,
    size: number,
    electionId: string
  ) {
    let param = `?page=${page}&size=${size}`;
    return (
      await this.getCall<PeendingElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/pending/${electionId}${param}`
      )
    ).payload;
  }
}
