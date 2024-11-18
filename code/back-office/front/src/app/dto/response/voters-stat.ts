export class VotersStat {
  electionId: number;
  election: string;
  voters: number;
  registered: number;
  maleUnder36: number;
  femaleUnder36: number;
  male36AndOver: number;
  female36AndOver: number;
  disabledPeople: number;
  visuallyImpairedPeople: number;

  constructor(
    electionId: number,
    election: string,
    voters: number,
    registered: number,
    maleUnder36: number,
    femaleUnder36: number,
    male36AndOver: number,
    female36AndOver: number,
    disabledPeople: number,
    visuallyImpairedPeople: number
  ) {
    this.electionId = electionId;
    this.election = election;
    this.voters = voters;
    this.registered = registered;
    this.maleUnder36 = maleUnder36;
    this.femaleUnder36 = femaleUnder36;
    this.male36AndOver = male36AndOver;
    this.female36AndOver = female36AndOver;
    this.disabledPeople = disabledPeople;
    this.visuallyImpairedPeople = visuallyImpairedPeople;
  }
}
