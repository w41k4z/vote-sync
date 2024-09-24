import { PollingStation } from '../../polling-station';

export class PollingStationPayload {
  pollingStations: PollingStation[];

  constructor(pollingStations: PollingStation[]) {
    this.pollingStations = pollingStations;
  }
}
