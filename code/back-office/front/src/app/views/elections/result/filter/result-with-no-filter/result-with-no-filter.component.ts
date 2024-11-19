import { Component, Input } from '@angular/core';
import { ElectoralResult } from '../../../../../dto/electoral-result';

@Component({
  selector: 'app-result-with-no-filter',
  templateUrl: './result-with-no-filter.component.html',
  styleUrl: './result-with-no-filter.component.scss',
})
export class ResultWithNoFilterComponent {
  @Input() current!: number;

  @Input() electoralResult!: ElectoralResult;

  @Input() page!: number;

  @Input() showExportButton!: boolean;

  divisions = [
    'Bureau de vote',
    'Fokontany',
    'Commune',
    'District',
    'Région',
    'Province',
    'Pays',
  ];
}
