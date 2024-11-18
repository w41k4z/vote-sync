export class AdministrativeDivisionStats {
  divionId: number;
  divisionName: string;
  electionTypeId: number;
  electionType: string;
  voters: number;
  registered: number;
  importedResults: number;
  collectedResults: number;
  totalPollingStationCount: number;
  alerts: number;
  geojson: any;

  constructor(
    divionId: number,
    divisionName: string,
    electionTypeId: number,
    electionType: string,
    voters: number,
    registered: number,
    importedResults: number,
    collectedResults: number,
    totalPollingStationCount: number,
    alerts: number,
    geojson: any
  ) {
    this.divionId = divionId;
    this.divisionName = divisionName;
    this.electionTypeId = electionTypeId;
    this.electionType = electionType;
    this.voters = voters;
    this.registered = registered;
    this.importedResults = importedResults;
    this.collectedResults = collectedResults;
    this.totalPollingStationCount = totalPollingStationCount;
    this.alerts = alerts;
    this.geojson = geojson;
  }
}
