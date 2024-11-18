import { AdministrativeDivisionStats } from '../../../administrative-division-stats';

export class AdministrativeDivisionStatsPayload {
  stats: AdministrativeDivisionStats[];

  constructor(administrativeDivisions: AdministrativeDivisionStats[]) {
    this.stats = administrativeDivisions;
  }
}
