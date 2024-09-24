import { AdministrativeDivision } from '../../administrative-division';
import { PollingStation } from '../../polling-station';
import { PagedModel } from '../paged-model-payload.response';

export class PollingStationPayload {
  pollingStations: PagedModel<PollingStation>;
  regions: AdministrativeDivision[];

  constructor(
    pollingStations: PagedModel<PollingStation>,
    regions: AdministrativeDivision[]
  ) {
    this.pollingStations = pollingStations;
    this.regions = regions;
  }
}
