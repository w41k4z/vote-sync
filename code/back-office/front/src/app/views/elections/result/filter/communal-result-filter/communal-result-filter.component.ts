import { Component, Input } from '@angular/core';
import { AdministrativeDivision } from '../../../../../dto/administrative-division';
import { ElectoralResult } from '../../../../../dto/electoral-result';

@Component({
  selector: 'app-communal-result-filter',
  templateUrl: './communal-result-filter.component.html',
  styleUrl: './communal-result-filter.component.scss',
})
export class CommunalResultFilterComponent {
  @Input() electoralResult!: ElectoralResult;
  @Input() regions!: AdministrativeDivision[];
  @Input() districts!: AdministrativeDivision[];

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

  @Input() regionId!: string;
  @Input() districtId!: string;

  @Input() showExportButton!: boolean;
}
