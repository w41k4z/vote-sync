export class AdministrativeDivisionStats {
  divisionId: number;
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
    divisionId: number,
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
    this.divisionId = divisionId;
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
