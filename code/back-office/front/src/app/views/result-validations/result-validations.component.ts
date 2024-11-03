import { Component } from '@angular/core';
import { PeendingElectoralResult } from '../../dto/pending-electoral-result';
import { Observable } from 'rxjs';
import { PendingElectoralResultService } from '../../services/api/election/election-result/pending-electoral-result.service';
import { Page } from '../../dto/response/page';
import { Router } from '@angular/router';
import { Paths } from '../../paths';

@Component({
  selector: 'app-result-validations',
  templateUrl: './result-validations.component.html',
  styleUrl: './result-validations.component.scss',
})
export class ResultValidationsComponent {
  loading$: Observable<Boolean>;
  error$: Observable<string | null>;
  message$: Observable<string | null>;
  pendingElectoralResults: PeendingElectoralResult[] = [];
  page: Page | null = null;
  pageSize = 5;

  constructor(
    private router: Router,
    private pendingElectoralResultService: PendingElectoralResultService
  ) {
    this.loading$ = pendingElectoralResultService.loading$;
    this.error$ = pendingElectoralResultService.error$;
    this.message$ = pendingElectoralResultService.message$;
    this.filter();
  }

  filter(page: number = 0) {
    this.pendingElectoralResultService
      .getPendingElectoralResults(page, this.pageSize)
      .then((payload) => {
        if (payload) {
          this.pendingElectoralResults = payload.electoralResults.content;
          this.page = payload.electoralResults.page;
        }
      });
  }

  nextPage = () => {
    if (this.page) {
      if (this.page.number + 1 < this.page.totalPages) {
        this.filter(this.page.number + 1);
      }
    }
  };

  previousPage = () => {
    if (this.page) {
      if (this.page.number - 1 >= 0) {
        this.filter(this.page.number - 1);
      }
    }
  };

  viewResult(pendingElectoralResult: PeendingElectoralResult) {
    this.router.navigate([`${Paths.RESULT_VALIDATIONS}/form`], {
      state: { pendingElectoralResult },
    });
  }
}
