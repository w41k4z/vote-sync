export class Alert {
  id: number;
  alertTypeId: number;
  alertType: string;
  alertLevel: number;
  electionId: number;
  election: string;
  pollingStationId: number;
  pollingStation: string;
  alertDate: string;
  description: string;
  status: number;

  constructor(
    id: number,
    alertTypeId: number,
    alertType: string,
    alertLevel: number,
    electionId: number,
    election: string,
    pollingStationId: number,
    pollingStation: string,
    alertDate: string,
    description: string,
    status: number
  ) {
    this.id = id;
    this.alertTypeId = alertTypeId;
    this.alertType = alertType;
    this.alertLevel = alertLevel;
    this.electionId = electionId;
    this.election = election;
    this.pollingStationId = pollingStationId;
    this.pollingStation = pollingStation;
    this.alertDate = alertDate;
    this.description = description;
    this.status = status;
  }
}
