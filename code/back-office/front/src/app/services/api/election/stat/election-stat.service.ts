import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiCallService } from '../../api-call';
import { AdministrativeDivisionStatsPayload } from '../../../../dto/response/election/stat/administrative-division-stats-payload.response';
import { Endpoints } from '../../../../endpoints';
import { VotersStatPayload } from '../../../../dto/response/election/stat/voters-stat-payload.response';
import { ElectoralResultPayload } from '../../../../dto/response/election/result/electoral-result-payload.response';

@Injectable({
  providedIn: 'root',
})
export class ElectionStatService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getVotersStats() {
    return (
      await this.getCall<VotersStatPayload>(`${Endpoints.ELECTION_STATS}`)
    ).payload;
  }

  async getAdministrativeDivisionStats(
    administrativeDivision: 'region' | 'district' | 'commune' | 'fokontany',
    electionTypeId?: number
  ) {
    let param = electionTypeId ? `?electionTypeId=${electionTypeId}` : '';
    return (
      await this.getCall<AdministrativeDivisionStatsPayload>(
        `${Endpoints.ELECTION_STATS}/${administrativeDivision}${param}`
      )
    ).payload;
  }

  async getAdministrativeDivisionStatDetails(
    administrativeDivision: 'region' | 'district' | 'commune' | 'fokontany',
    divisionId: number,
    page: number,
    electionTypeId?: number
  ) {
    let param = `?divisionId=${divisionId}`;
    param += electionTypeId ? `&electionTypeId=${electionTypeId}` : '';
    param += `&page=${page}`;
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_STATS}/${administrativeDivision}/details${param}`
      )
    ).payload;
  }
}
