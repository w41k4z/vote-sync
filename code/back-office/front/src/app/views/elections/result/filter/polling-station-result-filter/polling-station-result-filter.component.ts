import { Component, Input } from '@angular/core';
import { AdministrativeDivision } from '../../../../../dto/administrative-division';
import { ElectoralResult } from '../../../../../dto/electoral-result';

@Component({
  selector: 'app-polling-station-result-filter',
  templateUrl: './polling-station-result-filter.component.html',
  styleUrl: './polling-station-result-filter.component.scss',
})
export class PollingStationResultFilterComponent {
  @Input() electoralResult!: ElectoralResult;
  @Input() regions!: AdministrativeDivision[];
  @Input() districts!: AdministrativeDivision[];
  @Input() communes!: AdministrativeDivision[];
  @Input() fokontany!: AdministrativeDivision[];

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
  @Input() onCommuneFilterChange!: (communeId: string) => void;

  @Input() regionId!: string;
  @Input() districtId!: string;
  @Input() communeId!: string;
  @Input() fokontanyId!: string;

  @Input() showExportButton!: boolean;
}
