import { Component, Input } from '@angular/core';
import { AdministrativeDivision } from '../../../../../dto/administrative-division';
import { ElectoralResult } from '../../../../../dto/electoral-result';

@Component({
  selector: 'app-district-result-filter',
  templateUrl: './district-result-filter.component.html',
  styleUrl: './district-result-filter.component.scss',
})
export class DistrictResultFilterComponent {
  @Input() electoralResult!: ElectoralResult;
  @Input() regions!: AdministrativeDivision[];

  @Input() onFilter!: (
    page: number,
    regionId: string,
    districtId: string,
    communeId: string,
    fokontanyId: string
  ) => void;

  @Input() onExport!: (
    regionId: string,
    districtId: string,
    communeId: string,
    fokontanyId: string
  ) => void;

  @Input() page!: number;

  @Input() regionId!: string;

  @Input() showExportButton!: boolean;
}
