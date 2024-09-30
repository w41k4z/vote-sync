import { ElectoralResult } from '../../../electoral-result';

export class ElectoralResultPayload {
  electoralResults: ElectoralResult[];

  constructor(electoralResults: ElectoralResult[]) {
    this.electoralResults = electoralResults;
  }
}
