import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { HttpClient } from '@angular/common/http';
import { Endpoints } from '../../../endpoints';
import { AdministrativeDivisionsPayload } from '../../../dto/response/administrative-division/administrative-divisions-payload.reponse';

@Injectable({
  providedIn: 'root',
})
export class AdministrativeDivisionService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getRegions() {
    return (
      await this.getCall<AdministrativeDivisionsPayload>(
        `${Endpoints.ADMINISTRATIVE_DIVISION}/regions`
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

  async getMunicipalityDistrictsByRegionId(regionId: number) {
    const param = `upperDivisionId=${regionId}`;
    return (
      await this.getCall<AdministrativeDivisionsPayload>(
        `${Endpoints.ADMINISTRATIVE_DIVISION}/municipality-districts/by-region?${param}`
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

  async getMunicipalitiesByDistrictId(districtId: number) {
    const param = `upperDivisionId=${districtId}`;
    return (
      await this.getCall<AdministrativeDivisionsPayload>(
        `${Endpoints.ADMINISTRATIVE_DIVISION}/municipalities/by-district?${param}`
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

  async getFokontanyByMunicipalityId(municipalityId: number) {
    const param = `upperDivisionId=${municipalityId}`;
    return (
      await this.getCall<AdministrativeDivisionsPayload>(
        `${Endpoints.ADMINISTRATIVE_DIVISION}/municipality-fokontany/by-municipality?${param}`
      )
    ).payload;
  }
}
