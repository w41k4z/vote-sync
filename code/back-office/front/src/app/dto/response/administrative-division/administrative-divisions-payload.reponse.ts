import { AdministrativeDivision } from '../../administrative-division';

export class AdministrativeDivisionsPayload {
  administrativeDivisions: AdministrativeDivision[];

  constructor(administrativeDivisions: AdministrativeDivision[]) {
    this.administrativeDivisions = administrativeDivisions;
  }
}
