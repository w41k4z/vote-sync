import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { HttpClient } from '@angular/common/http';
import { Endpoints } from '../../../endpoints';
import { Election } from '../../../dto/election';

@Injectable({
  providedIn: 'root',
})
export class ElectionService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getElection(id: string) {
    return (
      await this.getCall<{ election: Election }>(`${Endpoints.ELECTIONS}/${id}`)
    ).payload;
  }

  async closeElection(electionId: number) {
    return (await this.postCall(`${Endpoints.ELECTIONS}/close`, electionId))
      .payload;
  }
}
