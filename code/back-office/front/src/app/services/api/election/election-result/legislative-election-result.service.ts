import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiCallService } from '../../api-call';
import { ElectoralResultPayload } from '../../../../dto/response/election/result/electoral-result-payload.response';
import { Endpoints } from '../../../../endpoints';

@Injectable({
  providedIn: 'root',
})
export class LegislativeElectionResultService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getDistrictResults(
    page: number,
    size: number,
    electionId: string,
    regionId: string
  ) {
    let params: string[] = [];
    params.push(`page=${page}`);
    params.push(`size=${size}`);
    if (regionId && regionId !== '*') {
      params.push(`regionId=${regionId}`);
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
        `${Endpoints.ELECTION_RESULTS}/legislative/district${strParam}`
      )
    ).payload;
  }

  async getDistrictResultsPdf(electionId: string, regionId: string) {
    let params: string[] = [];
    if (regionId && regionId !== '*') {
      params.push(`regionId=${regionId}`);
    }
    let strParam = `?electionId=${electionId}`;
    if (params.length > 0) {
      strParam += '&';
      for (let each of params) {
        strParam += each + '&';
      }
      strParam = strParam.slice(0, strParam.length - 1);
    }
    this.downloadCall(
      `${Endpoints.ELECTION_RESULTS}/exportation/legislative/district${strParam}`,
      'Resultats.pdf'
    );
  }

  async getCommunalResults(
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
        `${Endpoints.ELECTION_RESULTS}/legislative/communal${strParam}`
      )
    ).payload;
  }

  async getCommunalResultsPdf(
    electionId: string,
    regionId: string,
    districtId: string
  ) {
    let params: string[] = [];
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
    this.downloadCall(
      `${Endpoints.ELECTION_RESULTS}/exportation/legislative/communal${strParam}`,
      'Resultats.pdf'
    );
  }

  async getFokontanyResults(
    page: number,
    size: number,
    electionId: string,
    regionId: string,
    districtId: string,
    communeId: string
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
        `${Endpoints.ELECTION_RESULTS}/fokontany${strParam}`
      )
    ).payload;
  }

  async getFokontanyResultsPdf(
    electionId: string,
    regionId: string,
    districtId: string,
    communeId: string
  ) {
    let params: string[] = [];
    if (regionId && regionId !== '*') {
      params.push(`regionId=${regionId}`);
    }
    if (districtId && districtId !== '*') {
      params.push(`districtId=${districtId}`);
    }
    if (communeId && communeId !== '*') {
      params.push(`communeId=${communeId}`);
    }
    let strParam = `?electionId=${electionId}`;
    if (params.length > 0) {
      strParam += '&';
      for (let each of params) {
        strParam += each + '&';
      }
      strParam = strParam.slice(0, strParam.length - 1);
    }
    this.downloadCall(
      `${Endpoints.ELECTION_RESULTS}/exportation/fokontany${strParam}`,
      'Resultats.pdf'
    );
  }

  async getPollingStationResults(
    page: number,
    size: number,
    electionId: string,
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
        `${Endpoints.ELECTION_RESULTS}/polling-station${strParam}`
      )
    ).payload;
  }

  async getPollingStationResultsPdf(
    electionId: string,
    regionId: string,
    districtId: string,
    communeId: string,
    fokontanyId: string
  ) {
    let params: string[] = [];
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
    let strParam = `?electionId=${electionId}`;
    if (params.length > 0) {
      strParam += '&';
      for (let each of params) {
        strParam += each + '&';
      }
      strParam = strParam.slice(0, strParam.length - 1);
    }
    this.downloadCall(
      `${Endpoints.ELECTION_RESULTS}/exportation/polling-station${strParam}`,
      'Resultats.pdf'
    );
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
