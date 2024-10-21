import { ElectionType } from '../../election-type';

export class ElectionTypeListPayload {
  electionTypes: ElectionType[];

  constructor(electionTypes: ElectionType[]) {
    this.electionTypes = electionTypes;
  }
}
