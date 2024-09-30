import { ElectoralResultDetail } from './electoral-result-detail';

export class ElectoralResult {
  id: number;
  electionId: number;
  code: string;
  name: string;
  registeredVoters: number;
  blankVotes: number;
  nullVotes: number;
  validVotes: number;
  details: ElectoralResultDetail[];

  constructor(
    id: number,
    electionId: number,
    code: string,
    name: string,
    registeredVoters: number,
    blankVotes: number,
    nullVotes: number,
    validVotes: number,
    details: ElectoralResultDetail[]
  ) {
    this.id = id;
    this.electionId = electionId;
    this.code = code;
    this.name = name;
    this.registeredVoters = registeredVoters;
    this.blankVotes = blankVotes;
    this.nullVotes = nullVotes;
    this.validVotes = validVotes;
    this.details = details;
  }
}
