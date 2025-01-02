import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { Endpoints } from '../../../endpoints';
import { ElectionArchivePayload } from '../../../dto/response/election/election-archive-payload.reponse';

@Injectable({
  providedIn: 'root',
})
export class ElectionArchiveService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getElectionArchive(page: number = 0, size: number = 3) {
    let params: string[] = [];
    params.push(`page=${page}`);
    params.push(`size=${size}`);
    let strParam = `?${params.join('&')}`;
    return (
      await this.getCall<ElectionArchivePayload>(
        `${Endpoints.ELECTIONS}/history${strParam}`
      )
    ).payload;
  }
}
