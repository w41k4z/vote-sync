export class ValidateElectoralResultRequest {
  resultId: number;
  blankVotes: number;
  nullVotes: number;
  registeredVoters: number;
  resultDetails: { [key: string]: string };

  constructor(
    resultId: number,
    blankVotes: number,
    nullVotes: number,
    registeredVoters: number,
    resultDetails: { [key: string]: string }
  ) {
    this.resultId = resultId;
    this.blankVotes = blankVotes;
    this.nullVotes = nullVotes;
    this.registeredVoters = registeredVoters;
    this.resultDetails = resultDetails;
  }
}
