export class PendingElectoralResult {
  id: number;
  electionId: number;
  election: string;
  pollingStationId: number;
  pollingStation: string;
  voters: number;
  maleUnder36: number;
  femaleUnder36: number;
  male36AndOver: number;
  female36AndOver: number;
  disabledPeople: number;
  visuallyImpairedPeople: number;
  blankVotes: number;
  nullVotes: number;
  details: {
    id: number;
    candidateRegistrationId: number;
    candidateId: number;
    candidateInformation: string;
    candidateImagePath: string;
    politicalEntityId: number;
    politicalEntity: string;
    politicalEntityDescription: string;
    votes: number;
  }[];
  images: {
    imagePath: string;
  }[];
  status: number;

  constructor(
    id: number,
    electionId: number,
    election: string,
    pollingStationId: number,
    pollingStation: string,
    voters: number,
    maleUnder36: number,
    femaleUnder36: number,
    male36AndOver: number,
    female36AndOver: number,
    disabledPeople: number,
    visuallyImpairedPeople: number,
    blankVotes: number,
    nullVotes: number,
    details: {
      id: number;
      candidateRegistrationId: number;
      candidateId: number;
      candidateInformation: string;
      candidateImagePath: string;
      politicalEntityId: number;
      politicalEntity: string;
      politicalEntityDescription: string;
      votes: number;
    }[],
    images: {
      imagePath: string;
    }[],
    status: number
  ) {
    this.id = id;
    this.electionId = electionId;
    this.election = election;
    this.pollingStationId = pollingStationId;
    this.pollingStation = pollingStation;
    this.voters = voters;
    this.maleUnder36 = maleUnder36;
    this.femaleUnder36 = femaleUnder36;
    this.male36AndOver = male36AndOver;
    this.female36AndOver = female36AndOver;
    this.disabledPeople = disabledPeople;
    this.visuallyImpairedPeople = visuallyImpairedPeople;
    this.blankVotes = blankVotes;
    this.nullVotes = nullVotes;
    this.details = details;
    this.images = images;
    this.status = status;
  }
}
