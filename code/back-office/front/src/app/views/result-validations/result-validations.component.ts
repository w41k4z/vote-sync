import { Component } from '@angular/core';
import { PeendingElectoralResult } from '../../dto/pending-electoral-result';
import { PendingElectoralResultService } from '../../services/api/election/election-result/pending-electoral-result.service';
import { Page } from '../../dto/response/page';

@Component({
  selector: 'app-result-validations',
  templateUrl: './result-validations.component.html',
  styleUrl: './result-validations.component.scss',
})
export class ResultValidationsComponent {
  pendingElectoralResults: PeendingElectoralResult[] = [];
  page: Page | null = null;

  displayImageDialog: boolean = false;
  selectedImage: any;

  constructor(
    private pendingElectoralResultService: PendingElectoralResultService
  ) {
    this.filter();
  }

  filter(page: number = 0) {
    let electionId = '1';
    this.pendingElectoralResultService
      .getPendingElectoralResults(page, 1, electionId)
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

  showImage(image: { imagePath: string }) {
    this.selectedImage = image;
    this.displayImageDialog = true;
  }
}
