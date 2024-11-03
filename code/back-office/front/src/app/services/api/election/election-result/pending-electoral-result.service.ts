import { Injectable } from '@angular/core';
import { ApiCallService } from '../../api-call';
import { HttpClient } from '@angular/common/http';
import { PendingElectoralResultPayload } from '../../../../dto/response/election/result/pending-electoral-result-payload.response';
import { Endpoints } from '../../../../endpoints';
import { ValidateElectoralResultRequest } from '../../../../dto/request/result/validate-electoral-result-request';

@Injectable({
  providedIn: 'root',
})
export class PendingElectoralResultService extends ApiCallService {
  constructor(httpClient: HttpClient) {
    super(httpClient);
  }

  async getPendingElectoralResults(page: number, size: number) {
    let param = `?page=${page}&size=${size}`;
    return (
      await this.getCall<PendingElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/pending${param}`
      )
    ).payload;
  }

  async validateElectoralResult(request: ValidateElectoralResultRequest) {
    try {
      let response = await this.postCall(
        `${Endpoints.ELECTION_RESULTS}/validate`,
        request
      );
      return response;
    } catch (error: any) {
      this.errorSubject.next(error.error.errors.source);
      setTimeout(() => {
        this.errorSubject.next(null);
      }, 5000);
      throw error;
    }
  }
}
