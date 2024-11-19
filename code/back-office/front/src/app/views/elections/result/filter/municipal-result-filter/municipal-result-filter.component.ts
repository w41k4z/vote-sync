import { Component, Input } from '@angular/core';
import { ElectoralResult } from '../../../../../dto/electoral-result';
import { AdministrativeDivision } from '../../../../../dto/administrative-division';

@Component({
  selector: 'app-municipal-result-filter',
  templateUrl: './municipal-result-filter.component.html',
  styleUrl: './municipal-result-filter.component.scss',
})
export class MunicipalResultFilterComponent {
  @Input() electoralResult!: ElectoralResult;
  @Input() regions!: AdministrativeDivision[];
  @Input() districts!: AdministrativeDivision[];

  @Input() onFilter!: (
    page: number,
    regionId: string,
    districtId: string,
    municipalityId: string,
    fokontanyId: string
  ) => void;
  @Input() page!: number;

  @Input() onRegionFilterChange!: (regionId: string) => void;

  @Input() regionId!: string;
  @Input() districtId!: string;

  @Input() showExportButton!: boolean;
}
