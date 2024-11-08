import { Component, Input } from '@angular/core';
import { ElectoralResult } from '../../../../../dto/electoral-result';

@Component({
  selector: 'app-municipal-result-filter',
  templateUrl: './municipal-result-filter.component.html',
  styleUrl: './municipal-result-filter.component.scss',
})
export class MunicipalResultFilterComponent {
  @Input() currentResult!: number;
  @Input() electoralResult!: ElectoralResult;
}
