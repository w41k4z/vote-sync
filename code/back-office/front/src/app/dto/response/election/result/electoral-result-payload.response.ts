import { ElectoralResult } from '../../../electoral-result';
import { PagedModel } from '../../paged-model-payload.response';

export class ElectoralResultPayload {
  electoralResults: PagedModel<ElectoralResult>;

  constructor(electoralResults: PagedModel<ElectoralResult>) {
    this.electoralResults = electoralResults;
  }
}
