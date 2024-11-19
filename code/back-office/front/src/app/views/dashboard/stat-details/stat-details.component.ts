import { ChangeDetectorRef, Component, Inject } from '@angular/core';
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
    private cdr: ChangeDetectorRef,
    public dialogRef: MatDialogRef<StatDetailsComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: {
      division: 'region' | 'district' | 'commune' | 'fokontany';
      divisionId: number;
      divisionName: string;
      electionTypeId?: number;
    }
  ) {
    this.filterElectoralResults();
  }

  filterElectoralResults(page = 0) {
    this.electionStatService
      .getAdministrativeDivisionStatDetails(
        this.data.division,
        this.data.divisionId,
        page,
        this.data.electionTypeId
      )
      .then((payload) => {
        if (payload) {
          this.electoralResults = payload.electoralResults.content;
          this.page = payload.electoralResults.page;
          this.cdr.detectChanges();
        }
      });
  }

  nextPage() {
    if (this.page) {
      if (this.page.number + 1 < this.page.totalPages) {
        this.filterElectoralResults(this.page.number + 1);
      }
    }
  }

  previousPage() {
    if (this.page) {
      if (this.page.number > 0) {
        this.filterElectoralResults(this.page.number - 1);
      }
    }
  }
}
