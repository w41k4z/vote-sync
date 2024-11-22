export class ValidateElectoralResultRequest {
  resultId: number;
  blanks: number;
  nulls: number;
  voters: number;
  maleUnder36: number;
  femaleUnder36: number;
  male36AndOver: number;
  female36AndOver: number;
  disabledPeople: number;
  visuallyImpairedPeople: number;
  candidates: { [key: string]: string };

  constructor(
    resultId: number,
    blankVotes: number,
    nullVotes: number,
    voters: number,
    maleUnder36: number,
    femaleUnder36: number,
    male36AndOver: number,
    female36AndOver: number,
    disabledPeople: number,
    visuallyImpairedPeople: number,
    candidates: { [key: string]: string }
  ) {
    this.resultId = resultId;
    this.blanks = blankVotes;
    this.nulls = nullVotes;
    this.voters = voters;
    this.maleUnder36 = maleUnder36;
    this.femaleUnder36 = femaleUnder36;
    this.male36AndOver = male36AndOver;
    this.female36AndOver = female36AndOver;
    this.disabledPeople = disabledPeople;
    this.visuallyImpairedPeople = visuallyImpairedPeople;
    this.candidates = candidates;
  }
}
