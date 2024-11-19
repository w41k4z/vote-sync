import { ElectoralResultDetail } from './electoral-result-detail';

export class ElectoralResult {
  divisionId: number;
  electionId: number;
  election: string;
  code: string;
  name: string;
  fokontany: string;
  commune: string;
  municipality: string;
  district: string;
  municipalityDistrict: string;
  region: string;
  voters: number;
  blankVotes: number;
  nullVotes: number;
  validVotes: number;
  importedResults: number;
  collectedResults: number;
  totalPollingStationCount: number;
  alerts: number;
  details: ElectoralResultDetail[];

  constructor(
    divisionId: number,
    electionId: number,
    election: string,
    code: string,
    name: string,
    fokontany: string,
    commune: string,
    municipality: string,
    district: string,
    municipalityDistrict: string,
    region: string,
    voters: number,
    blankVotes: number,
    nullVotes: number,
    validVotes: number,
    importedResults: number,
    collectedResults: number,
    totalPollingStationCount: number,
    alerts: number,
    details: ElectoralResultDetail[]
  ) {
    this.divisionId = divisionId;
    this.electionId = electionId;
    this.election = election;
    this.code = code;
    this.name = name;
    this.fokontany = fokontany;
    this.commune = commune;
    this.municipality = municipality;
    this.district = district;
    this.municipalityDistrict = municipalityDistrict;
    this.region = region;
    this.voters = voters;
    this.blankVotes = blankVotes;
    this.nullVotes = nullVotes;
    this.validVotes = validVotes;
    this.importedResults = importedResults;
    this.collectedResults = collectedResults;
    this.totalPollingStationCount = totalPollingStationCount;
    this.alerts = alerts;
    this.details = details;
  }
}
