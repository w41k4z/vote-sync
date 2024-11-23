import { Alert } from '../alert';
import { PagedModel } from './paged-model-payload.response';

export class AlertPayloadResponse {
  alerts: PagedModel<Alert>;

  constructor(alerts: PagedModel<Alert>) {
    this.alerts = alerts;
  }
}
