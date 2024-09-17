import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { HttpClient } from '@angular/common/http';
import { RegionStatPayload } from '../../../dto/response/stat/region-stat-payload.response';

@Injectable({
  providedIn: 'root',
})
export class StatService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getRegionsStat() {
    return (await this.getCall<RegionStatPayload>('api/stats/regions')).payload;
  }
}
