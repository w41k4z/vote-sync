import { Component } from '@angular/core';
import { PendingElectoralResult } from '../../../dto/pending-electoral-result';
import { PendingElectoralResultService } from '../../../services/api/election/election-result/pending-electoral-result.service';
import { ValidateElectoralResultRequest } from '../../../dto/request/result/validate-electoral-result-request';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-result-validation-form',
  templateUrl: './result-validation-form.component.html',
  styleUrl: './result-validation-form.component.scss',
})
export class ResultValidationFormComponent {
  pendingElectoralResult: PendingElectoralResult;

  loading$: Observable<Boolean>;
  error$: Observable<string | null>;
  message$: Observable<string | null>;

  displayImageDialog: boolean = false;
  selectedImage: any;

  constructor(
    private pendingElectoralResultService: PendingElectoralResultService
  ) {
    this.pendingElectoralResult = history.state.pendingElectoralResult;
    this.loading$ = pendingElectoralResultService.loading$;
    this.error$ = pendingElectoralResultService.error$;
    this.message$ = pendingElectoralResultService.message$;
  }

  showImage(image: { imagePath: string }) {
    this.selectedImage = image;
    this.displayImageDialog = true;
  }

  checkResultValidity() {
    let result = 0;
    let totalVotes = 0;
    if (this.pendingElectoralResult.nullVotes == null) {
      alert('Veuillez saisir le nombre de votes nuls');
      return -1;
    }
    if (this.pendingElectoralResult.blankVotes == null) {
      alert('Veuillez saisir le nombre de votes blancs');
      return -1;
    }
    if (this.pendingElectoralResult.registeredVoters == null) {
      alert('Veuillez saisir le nombre de votants enregistrés');
      return -1;
    }
    if (this.pendingElectoralResult.nullVotes < 0) {
      alert('Le nombre de votes nuls doit être positif');
      return -1;
    }
    if (this.pendingElectoralResult.blankVotes < 0) {
      alert('Le nombre de votes blancs doit être positif');
      return -1;
    }
    if (this.pendingElectoralResult.registeredVoters < 0) {
      alert('Le nombre de votants enregistrés doit être positif');
      return -1;
    }
    totalVotes =
      this.pendingElectoralResult.blankVotes +
      this.pendingElectoralResult.nullVotes;
    for (let detail of this.pendingElectoralResult.details) {
      if (detail.votes == null) {
        alert(
          `Veuillez saisir le nombre de voix obtenues pour ${detail.candidateInformation}`
        );
        return -1;
      }
      if (detail.votes < 0) {
        alert(
          `Le nombre de voix obtenues pour ${detail.candidateInformation} doit être positif`
        );
        return -1;
      }
      totalVotes += detail.votes;
    }
    if (totalVotes != this.pendingElectoralResult.registeredVoters) {
      alert(
        'Le resultat est incorrecte. Verifier que la somme des voix, des blancs et nuls est égale aux nombre de votants enregistrés'
      );
      return -1;
    }
    return result;
  }

  async submit() {
    if (this.checkResultValidity() == -1) return;
    let resultDetails: { [key: string]: string } = {};
    for (let detail of this.pendingElectoralResult.details) {
      resultDetails[detail.id.toString()] = detail.votes.toString();
    }
    await this.pendingElectoralResultService.validateElectoralResult(
      new ValidateElectoralResultRequest(
        this.pendingElectoralResult.id,
        this.pendingElectoralResult.blankVotes,
        this.pendingElectoralResult.nullVotes,
        this.pendingElectoralResult.registeredVoters,
        resultDetails
      )
    );
    this.pendingElectoralResult.status = 20;
  }
}
