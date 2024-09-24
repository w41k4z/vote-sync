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

  async getPollingStations() {
    return (
      await this.getCall<PollingStationPayload>(Endpoints.POLLING_STATIONS)
    ).payload;
  }
}
