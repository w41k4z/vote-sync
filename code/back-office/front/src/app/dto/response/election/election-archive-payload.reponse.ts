import { Election } from '../../election';
import { PagedModel } from '../paged-model-payload.response';

export class ElectionArchivePayload {
  elections: PagedModel<Election>;

  constructor(elections: PagedModel<Election>) {
    this.elections = elections;
  }
}
