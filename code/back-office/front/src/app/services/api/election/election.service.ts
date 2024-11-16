import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { HttpClient } from '@angular/common/http';
import { CurrentElectionsPayload } from '../../../dto/response/election/current-elections-payload.response';
import { Endpoints } from '../../../endpoints';
import { Election } from '../../../dto/election';
import { ConfigureElectionRequest } from '../../../dto/request/configure-election-request';
import { SaveElectionPayload } from '../../../dto/response/election/save-election-payload.response';
import { UpdateElectionRequest } from '../../../dto/request/update-election-request';

@Injectable({
  providedIn: 'root',
})
export class ElectionService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async configureElection(configureElectionRequest: ConfigureElectionRequest) {
    return (
      await this.postCall<SaveElectionPayload>(
        Endpoints.ELECTIONS,
        configureElectionRequest
      )
    ).payload;
  }

  async importElectoralResults(importElectoralResultsRequest: {
    electionId: number;
    file: File;
    password: string;
  }) {
    const formData = new FormData();
    formData.append(
      'electionId',
      importElectoralResultsRequest.electionId.toString()
    );
    formData.append('file', importElectoralResultsRequest.file);
    formData.append('password', importElectoralResultsRequest.password);
    return (
      await this.postCall(`${Endpoints.ELECTION_RESULTS}/import`, formData)
    ).payload;
  }

  async updateElection(updateElectionRequest: UpdateElectionRequest) {
    return (
      await this.putCall<SaveElectionPayload>(
        Endpoints.ELECTIONS,
        updateElectionRequest
      )
    ).payload;
  }

  async deleteElection(electionId: number) {
    return (
      await this.deleteCall<SaveElectionPayload>(
        `${Endpoints.ELECTIONS}?electionId=${electionId}`
      )
    ).payload;
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
