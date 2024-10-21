import { Injectable } from '@angular/core';
import { ApiCallService } from '../../api-call';
import { HttpClient } from '@angular/common/http';
import { ElectionTypeListPayload } from '../../../../dto/response/election/election-type-list-payload.response';
import { Endpoints } from '../../../../endpoints';

@Injectable({
  providedIn: 'root',
})
export class ElectionTypeService extends ApiCallService {
  constructor(httpClient: HttpClient) {
    super(httpClient);
  }

  async getElectionTypes() {
    return (
      await this.getCall<ElectionTypeListPayload>(Endpoints.ELECTION_TYPES)
    ).payload;
  }
}
