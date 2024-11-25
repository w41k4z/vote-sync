import { Component, Input } from '@angular/core';
import { ElectoralResult } from '../../../../../dto/electoral-result';
import { AdministrativeDivision } from '../../../../../dto/administrative-division';

@Component({
  selector: 'app-polling-station-local-result-filter',
  templateUrl: './polling-station-local-result-filter.component.html',
  styleUrl: './polling-station-local-result-filter.component.scss',
})
export class PollingStationLocalResultFilterComponent {
  @Input() electoralResult!: ElectoralResult;
  @Input() regions!: AdministrativeDivision[];
  @Input() districts!: AdministrativeDivision[];
  @Input() municipalities!: AdministrativeDivision[];
  @Input() fokontany!: AdministrativeDivision[];

  @Input() onFilter!: (
    page: number,
    regionId: string,
    districtId: string,
    municipalityId: string,
    fokontanyId: string
  ) => void;

  @Input() onExport!: (
    regionId: string,
    districtId: string,
    municipalityId: string,
    fokontanyId: string
  ) => void;

  @Input() page!: number;

  @Input() onRegionFilterChange!: (regionId: string) => void;
  @Input() onDistrictFilterChange!: (districtId: string) => void;
  @Input() onMunicipalityFilterChange!: (municipalityId: string) => void;

  @Input() regionId!: string;
  @Input() districtId!: string;
  @Input() municipalityId!: string;
  @Input() fokontanyId!: string;

  @Input() showExportButton!: boolean;
}
