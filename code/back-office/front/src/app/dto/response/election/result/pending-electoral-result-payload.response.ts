import { PendingElectoralResult } from '../../../pending-electoral-result';
import { PagedModel } from '../../paged-model-payload.response';

export class PendingElectoralResultPayload {
  electoralResults: PagedModel<PendingElectoralResult>;

  constructor(electoralResults: PagedModel<PendingElectoralResult>) {
    this.electoralResults = electoralResults;
  }
}
