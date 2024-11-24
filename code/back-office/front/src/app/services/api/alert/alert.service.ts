import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { HttpClient } from '@angular/common/http';
import { AlertPayloadResponse } from '../../../dto/response/alert-payload.response';
import { Endpoints } from '../../../endpoints';

@Injectable({
  providedIn: 'root',
})
export class AlertService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getAlertsCount() {
    return (
      await this.getCall<{ alertsCount: number }>(`${Endpoints.ALERTS}/count`)
    ).payload;
  }

  async updateAlertState(alertId: number, status: number) {
    return (
      await this.putCall(`${Endpoints.ALERTS}/update-status`, {
        alertId: alertId,
        status: status,
      })
    ).payload;
  }

  async getCurrentAlerts(
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
      await this.getCall<AlertPayloadResponse>(`${Endpoints.ALERTS}${strParam}`)
    ).payload;
  }
}
