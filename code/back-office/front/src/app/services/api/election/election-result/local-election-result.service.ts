import { Injectable } from '@angular/core';
import { ApiCallService } from '../../api-call';
import { HttpClient } from '@angular/common/http';
import { ElectoralResultPayload } from '../../../../dto/response/election/result/electoral-result-payload.response';
import { Endpoints } from '../../../../endpoints';

@Injectable({
  providedIn: 'root',
})
export class LocalElectionResultService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getMunicipalResults(
    page: number,
    size: number,
    electionId: string,
    regionId: string,
    districtId: string
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
    let strParam = `?electionId=${electionId}`;
    if (params.length > 0) {
      strParam += '&';
      for (let each of params) {
        strParam += each + '&';
      }
      strParam = strParam.slice(0, strParam.length - 1);
    }
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/local/municipal${strParam}`
      )
    ).payload;
  }

  async getFokontanyResults(
    page: number,
    size: number,
    electionId: string,
    regionId: string,
    districtId: string,
    municipalityId: string
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
    if (municipalityId && municipalityId !== '*') {
      params.push(`municipalityId=${municipalityId}`);
    }
    let strParam = `?electionId=${electionId}`;
    if (params.length > 0) {
      strParam += '&';
      for (let each of params) {
        strParam += each + '&';
      }
      strParam = strParam.slice(0, strParam.length - 1);
    }
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/local/fokontany${strParam}`
      )
    ).payload;
  }

  async getPollingStationResults(
    page: number,
    size: number,
    electionId: string,
    regionId: string,
    districtId: string,
    municipalityId: string,
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
    if (municipalityId && municipalityId !== '*') {
      params.push(`municipalityId=${municipalityId}`);
    }
    if (fokontanyId && fokontanyId !== '*') {
      params.push(`fokontanyId=${fokontanyId}`);
    }
    let strParam = `?electionId=${electionId}`;
    if (params.length > 0) {
      strParam += '&';
      for (let each of params) {
        strParam += each + '&';
      }
      strParam = strParam.slice(0, strParam.length - 1);
    }
    return (
      await this.getCall<ElectoralResultPayload>(
        `${Endpoints.ELECTION_RESULTS}/local/polling-station${strParam}`
      )
    ).payload;
  }

  async invalidateElectoralResult(
    electionId: number,
    pollingStationId: number
  ) {
    await this.putCall(`${Endpoints.ELECTION_RESULTS}/invalidate`, {
      electionId: electionId,
      pollingStationId: pollingStationId,
    });
  }
}
