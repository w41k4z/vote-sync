import { Election } from '../../election';

export class SaveElectionPayload {
  election: Election;

  constructor(election: Election) {
    this.election = election;
  }
}
