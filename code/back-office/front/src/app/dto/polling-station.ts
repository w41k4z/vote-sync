export class PollingStation {
  id: number;
  pollingStationCode: string;
  pollingStation: string;
  votingCenter: string;
  fokontany: string;
  commune: string;
  district: string;
  region: string;

  constructor(
    id: number,
    pollingStationCode: string,
    pollingStation: string,
    votingCenter: string,
    fokontany: string,
    commune: string,
    district: string,
    region: string
  ) {
    this.id = id;
    this.pollingStationCode = pollingStationCode;
    this.pollingStation = pollingStation;
    this.votingCenter = votingCenter;
    this.fokontany = fokontany;
    this.commune = commune;
    this.district = district;
    this.region = region;
  }
}
