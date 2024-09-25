import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { HttpClient } from '@angular/common/http';
import { AdministrativeDivisionStatsPayload } from '../../../dto/response/administrative-division/administrative-division-stats-payload.response';
import { Endpoints } from '../../../endpoints';
import { AdministrativeDivisionsPayload } from '../../../dto/response/administrative-division/administrative-divisions-payload.reponse';

@Injectable({
  providedIn: 'root',
})
export class AdministrativeDivisionService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getRegionsStat() {
    return (
      await this.getCall<AdministrativeDivisionStatsPayload>(
        `${Endpoints.ADMINISTRATIVE_DIVISION}/regions/stats`
      )
    ).payload;
  }

  async getDistrictsByRegionId(regionId: number) {
    const param = `upperDivisionId=${regionId}`;
    return (
      await this.getCall<AdministrativeDivisionsPayload>(
        `${Endpoints.ADMINISTRATIVE_DIVISION}/districts/by-region?${param}`
      )
    ).payload;
  }

  async getCommunesByDistrictId(districtId: number) {
    const param = `upperDivisionId=${districtId}`;
    return (
      await this.getCall<AdministrativeDivisionsPayload>(
        `${Endpoints.ADMINISTRATIVE_DIVISION}/communes/by-district?${param}`
      )
    ).payload;
  }

  async getFokontanyByCommuneId(communeId: number) {
    const param = `upperDivisionId=${communeId}`;
    return (
      await this.getCall<AdministrativeDivisionsPayload>(
        `${Endpoints.ADMINISTRATIVE_DIVISION}/fokontany/by-commune?${param}`
      )
    ).payload;
  }
}
