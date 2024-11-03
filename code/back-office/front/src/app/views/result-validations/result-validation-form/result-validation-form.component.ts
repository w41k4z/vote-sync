import { Component } from '@angular/core';
import { PeendingElectoralResult } from '../../../dto/pending-electoral-result';

@Component({
  selector: 'app-result-validation-form',
  templateUrl: './result-validation-form.component.html',
  styleUrl: './result-validation-form.component.scss',
})
export class ResultValidationFormComponent {
  pendingElectoralResult: PeendingElectoralResult;

  displayImageDialog: boolean = false;
  selectedImage: any;

  constructor() {
    this.pendingElectoralResult = history.state.pendingElectoralResult;
  }

  showImage(image: { imagePath: string }) {
    this.selectedImage = image;
    this.displayImageDialog = true;
  }
}
