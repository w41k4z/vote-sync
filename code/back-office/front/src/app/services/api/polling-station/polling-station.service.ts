import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { HttpClient } from '@angular/common/http';
import { PollingStationPayload } from '../../../dto/response/polling-station/polling-station-payload.reponse';
import { Endpoints } from '../../../endpoints';

@Injectable({
  providedIn: 'root',
})
export class PollingStationService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getPollingStations(
    page: number,
    size: number,
    regionId: string,
    districtId: string,
    communeId: string,
    fokontanyId: string
  ) {
    let params: string[] = [];
    params.push(`page=${page}`);
    params.push(`size=${size}`);
    if (regionId && regionId !== '*') {
      params.push(`regionId=${regionId}`);
    }
    if (districtId && districtId !== '*') {
      params.push(`districtId=${districtId}`);
    }
    if (communeId && communeId !== '*') {
      params.push(`communeId=${communeId}`);
    }
    if (fokontanyId && fokontanyId !== '*') {
      params.push(`fokontanyId=${fokontanyId}`);
    }
    let strParam = '';
    if (params.length > 0) {
      strParam = '?';
      for (let each of params) {
        strParam += each + '&';
      }
      strParam = strParam.slice(0, strParam.length - 1);
    }
    return (
      await this.getCall<PollingStationPayload>(
        `${Endpoints.POLLING_STATIONS}${strParam}`
      )
    ).payload;
  }

  async assignOperators() {
    return await this.getCall<any>(`${Endpoints.USERS}/assign-operators`);
  }
}
