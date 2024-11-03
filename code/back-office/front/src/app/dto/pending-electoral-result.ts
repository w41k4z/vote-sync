export class PeendingElectoralResult {
  id: number;
  electionId: number;
  election: string;
  pollingStationId: number;
  pollingStation: string;
  registeredVoters: number;
  blankVotes: number;
  nullVotes: number;
  details: {
    id: number;
    candidateRegistrationId: number;
    candidateId: number;
    candidateInformation: string;
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
    registeredVoters: number,
    blankVotes: number,
    nullVotes: number,
    details: {
      id: number;
      candidateRegistrationId: number;
      candidateId: number;
      candidateInformation: string;
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
    this.registeredVoters = registeredVoters;
    this.blankVotes = blankVotes;
    this.nullVotes = nullVotes;
    this.details = details;
    this.images = images;
    this.status = status;
  }
}
