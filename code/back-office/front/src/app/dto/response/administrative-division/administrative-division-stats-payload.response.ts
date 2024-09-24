import { AdministrativeDivisionStats } from '../../administrative-division-stats';

export class AdministrativeDivisionStatsPayload {
  administrativeDivisionStats: AdministrativeDivisionStats[];

  constructor(administrativeDivisionStats: AdministrativeDivisionStats[]) {
    this.administrativeDivisionStats = administrativeDivisionStats;
  }
}
