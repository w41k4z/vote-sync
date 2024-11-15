export class ValidateElectoralResultRequest {
  resultId: number;
  blanks: number;
  nulls: number;
  registeredVoters: number;
  candidates: { [key: string]: string };

  constructor(
    resultId: number,
    blankVotes: number,
    nullVotes: number,
    registeredVoters: number,
    candidates: { [key: string]: string }
  ) {
    this.resultId = resultId;
    this.blanks = blankVotes;
    this.nulls = nullVotes;
    this.registeredVoters = registeredVoters;
    this.candidates = candidates;
  }
}
