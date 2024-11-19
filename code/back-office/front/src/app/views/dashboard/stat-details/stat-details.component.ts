import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ElectionStatService } from '../../../services/api/election/stat/election-stat.service';
import { ElectoralResult } from '../../../dto/electoral-result';
import { Page } from '../../../dto/response/page';

@Component({
  selector: 'app-stat-details',
  templateUrl: './stat-details.component.html',
  styleUrl: './stat-details.component.scss',
})
export class StatDetailsComponent {
  electoralResults: ElectoralResult[] = [];
  page: Page | null = null;

  constructor(
    private electionStatService: ElectionStatService,
    public dialogRef: MatDialogRef<StatDetailsComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: {
      division: 'region' | 'district' | 'commune' | 'fokontany';
      divisionId: number;
      electionTypeId?: number;
    }
  ) {
    this.electionStatService
      .getAdministrativeDivisionStatDetails(
        this.data.division,
        this.data.divisionId,
        this.data.electionTypeId
      )
      .then((payload) => {
        if (payload) {
          console.log(payload);
          this.electoralResults = payload.electoralResults.content;
          this.page = payload.electoralResults.page;
        }
      });
  }
}
