import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { ConfigureElectionRequest } from '../../../dto/request/configure-election-request';
import { ElectionType } from '../../../dto/election-type';
import { FormContainerComponent } from '../../../components/form-container/form-container.component';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ElectionTypeService } from '../../../services/api/election/election-type/election-type.service';

@Component({
  selector: 'app-election-dialog',
  templateUrl: './election-dialog.component.html',
  styleUrl: './election-dialog.component.scss',
})
export class ElectionDialogComponent extends FormContainerComponent {
  electionTypes: ElectionType[] = [];

  constructor(
    private electionTypeService: ElectionTypeService,
    public dialogRef: MatDialogRef<ElectionDialogComponent>
  ) {
    const electionForm = new FormGroup({
      electionTypeId: new FormControl('', [Validators.required]),
      name: new FormControl('', [Validators.required]),
      electionDate: new FormControl('', [Validators.required]),
    });
    super(electionForm);
    this.electionTypeService.getElectionTypes().then((payload) => {
      if (payload) {
        this.electionTypes = payload.electionTypes;
      }
    });
  }

  override onConfirm() {
    const request: ConfigureElectionRequest = {
      electionTypeId: this.componentForm.value.electionTypeId,
      name: this.componentForm.value.name,
      startDate: this.componentForm.value.electionDate,
    };
    this.dialogRef.close(request);
  }
}
