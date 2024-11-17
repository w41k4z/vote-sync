import { ElectoralResultDetail } from './electoral-result-detail';

export class ElectoralResult {
  divisionId: number;
  electionId: number;
  code: string;
  name: string;
  fokontany: string;
  commune: string;
  municipality: string;
  district: string;
  municipalityDistrict: string;
  region: string;
  registeredVoters: number;
  blankVotes: number;
  nullVotes: number;
  validVotes: number;
  details: ElectoralResultDetail[];

  constructor(
    divisionId: number,
    electionId: number,
    code: string,
    name: string,
    fokontany: string,
    commune: string,
    municipality: string,
    district: string,
    municipalityDistrict: string,
    region: string,
    registeredVoters: number,
    blankVotes: number,
    nullVotes: number,
    validVotes: number,
    details: ElectoralResultDetail[]
  ) {
    this.divisionId = divisionId;
    this.electionId = electionId;
    this.code = code;
    this.name = name;
    this.fokontany = fokontany;
    this.commune = commune;
    this.municipality = municipality;
    this.district = district;
    this.municipalityDistrict = municipalityDistrict;
    this.region = region;
    this.registeredVoters = registeredVoters;
    this.blankVotes = blankVotes;
    this.nullVotes = nullVotes;
    this.validVotes = validVotes;
    this.details = details;
  }
}
