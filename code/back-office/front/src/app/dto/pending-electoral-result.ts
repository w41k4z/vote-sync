export class PeendingElectoralResult {
  id: number;
  electionId: number;
  pollingStationId: number;
  pollingStation: string;
  registeredVoters: number;
  blankVotes: number;
  nullVotes: number;
  details: {
    id: number;
    candidateRegistrationId: number;
    votes: number;
  }[];
  images: {
    imagePath: string;
  }[];

  constructor(
    id: number,
    electionId: number,
    pollingStationId: number,
    pollingStation: string,
    registeredVoters: number,
    blankVotes: number,
    nullVotes: number,
    details: {
      id: number;
      candidateRegistrationId: number;
      votes: number;
    }[],
    images: {
      imagePath: string;
    }[]
  ) {
    this.id = id;
    this.electionId = electionId;
    this.pollingStationId = pollingStationId;
    this.pollingStation = pollingStation;
    this.registeredVoters = registeredVoters;
    this.blankVotes = blankVotes;
    this.nullVotes = nullVotes;
    this.details = details;
    this.images = images;
  }
}
