import { AdministrativeDivisionStats } from '../../administrative-division-stats';

export class AdministrativeDivisionStatsPayload {
  administrativeDivisions: AdministrativeDivisionStats[];

  constructor(administrativeDivisions: AdministrativeDivisionStats[]) {
    this.administrativeDivisions = administrativeDivisions;
  }
}
