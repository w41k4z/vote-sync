import { Election } from '../../election';

export class CurrentElectionsPayload {
  elections: Election[];

  constructor(elections: Election[]) {
    this.elections = elections;
  }
}
