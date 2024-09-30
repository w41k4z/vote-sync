import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { HttpClient } from '@angular/common/http';
import { CurrentElectionsPayload } from '../../../dto/response/election/current-elections-payload.response';
import { Endpoints } from '../../../endpoints';
import { Election } from '../../../dto/election';

@Injectable({
  providedIn: 'root',
})
export class ElectionService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getCurrentElections() {
    return (
      await this.getCall<CurrentElectionsPayload>(
        `${Endpoints.ELECTIONS}/current`
      )
    ).payload;
  }

  async getElection(id: string) {
    return (
      await this.getCall<{ election: Election }>(`${Endpoints.ELECTIONS}/${id}`)
    ).payload;
  }
}
