export class ValidateElectoralResultRequest {
  resultId: number;
  blanks: number;
  nulls: number;
  registered: number;
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
    this.registered = registeredVoters;
    this.candidates = candidates;
  }
}
