import { Component, Input } from '@angular/core';
import { ElectoralResult } from '../../../../../dto/electoral-result';
import { AdministrativeDivision } from '../../../../../dto/administrative-division';

@Component({
  selector: 'app-fokontany-local-result-filter',
  templateUrl: './fokontany-local-result-filter.component.html',
  styleUrl: './fokontany-local-result-filter.component.scss',
})
export class FokontanyLocalResultFilterComponent {
  @Input() electoralResult!: ElectoralResult;
  @Input() regions!: AdministrativeDivision[];
  @Input() districts!: AdministrativeDivision[];
  @Input() municipalities!: AdministrativeDivision[];

  @Input() onFilter!: (
    page: number,
    regionId: string,
    districtId: string,
    municipalityId: string,
    fokontanyId: string
  ) => void;
  @Input() page!: number;

  @Input() onRegionFilterChange!: (regionId: string) => void;
  @Input() onDistrictFilterChange!: (districtId: string) => void;

  @Input() regionId!: string;
  @Input() districtId!: string;
  @Input() municipalityId!: string;

  @Input() showExportButton!: boolean;
}
