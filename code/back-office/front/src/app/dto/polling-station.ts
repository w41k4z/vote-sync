export class PollingStation {
  id: number;
  pollingStationCode: string;
  pollingStation: string;
  votingCenter: string;
  fokontany: string;
  commune: string;
  district: string;
  region: string;
  operatorName: string;
  operatorFirstName: string;

  constructor(
    id: number,
    pollingStationCode: string,
    pollingStation: string,
    votingCenter: string,
    fokontany: string,
    commune: string,
    district: string,
    region: string,
    operatorName: string,
    operatorFirstName: string
  ) {
    this.id = id;
    this.pollingStationCode = pollingStationCode;
    this.pollingStation = pollingStation;
    this.votingCenter = votingCenter;
    this.fokontany = fokontany;
    this.commune = commune;
    this.district = district;
    this.region = region;
    this.operatorName = operatorName;
    this.operatorFirstName = operatorFirstName;
  }
}
