import { PeendingElectoralResult } from '../../../pending-electoral-result';
import { PagedModel } from '../../paged-model-payload.response';

export class PeendingElectoralResultPayload {
  electoralResults: PagedModel<PeendingElectoralResult>;

  constructor(electoralResults: PagedModel<PeendingElectoralResult>) {
    this.electoralResults = electoralResults;
  }
}
