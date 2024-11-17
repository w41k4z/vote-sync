import { Election } from '../../election';

export class ElectionsPayload {
  elections: Election[];

  constructor(elections: Election[]) {
    this.elections = elections;
  }
}
