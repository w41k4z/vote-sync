import { Component, Input } from '@angular/core';
import { ElectoralResult } from '../../../../../dto/electoral-result';

@Component({
  selector: 'app-fokontany-local-result-filter',
  templateUrl: './fokontany-local-result-filter.component.html',
  styleUrl: './fokontany-local-result-filter.component.scss',
})
export class FokontanyLocalResultFilterComponent {
  @Input() electoralResult!: ElectoralResult;
}
