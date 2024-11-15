import { Component, Input } from '@angular/core';
import { ElectoralResult } from '../../../../../dto/electoral-result';

@Component({
  selector: 'app-polling-station-local-result-filter',
  templateUrl: './polling-station-local-result-filter.component.html',
  styleUrl: './polling-station-local-result-filter.component.scss',
})
export class PollingStationLocalResultFilterComponent {
  @Input() electoralResult!: ElectoralResult;
}
