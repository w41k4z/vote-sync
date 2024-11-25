import { Component, Input } from '@angular/core';
import { AdministrativeDivision } from '../../../../../dto/administrative-division';
import { ElectoralResult } from '../../../../../dto/electoral-result';

@Component({
  selector: 'app-fokontany-result-filter',
  templateUrl: './fokontany-result-filter.component.html',
  styleUrl: './fokontany-result-filter.component.scss',
})
export class FokontanyResultFilterComponent {
  @Input() electoralResult!: ElectoralResult;
  @Input() regions!: AdministrativeDivision[];
  @Input() districts!: AdministrativeDivision[];
  @Input() communes!: AdministrativeDivision[];

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

  @Input() onRegionFilterChange!: (regionId: string) => void;
  @Input() onDistrictFilterChange!: (districtId: string) => void;

  @Input() regionId!: string;
  @Input() districtId!: string;
  @Input() communeId!: string;

  @Input() showExportButton!: boolean;
}
