export class ElectoralResultDetail {
  id: number;
  electionId: number;
  code: string;
  locationId: number;
  location: string;
  candidateId: number;
  candidateNumber: number;
  candidateInformation: string;
  politicalEntityId: number;
  politicalEntity: string;
  candidateVotes: number;
  imagePath: string;

  constructor(
    id: number,
    electionId: number,
    code: string,
    locationId: number,
    location: string,
    candidateId: number,
    candidateNumber: number,
    candidateInformation: string,
    politicalEntityId: number,
    politicalEntity: string,
    candidateVotes: number,
    imagePath: string
  ) {
    this.id = id;
    this.electionId = electionId;
    this.code = code;
    this.locationId = locationId;
    this.location = location;
    this.candidateId = candidateId;
    this.candidateNumber = candidateNumber;
    this.candidateInformation = candidateInformation;
    this.politicalEntityId = politicalEntityId;
    this.politicalEntity = politicalEntity;
    this.candidateVotes = candidateVotes;
    this.imagePath = imagePath;
  }
}
