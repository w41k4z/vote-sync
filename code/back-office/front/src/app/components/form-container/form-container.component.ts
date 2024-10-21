import { FormGroup } from '@angular/forms';

export class FormContainerComponent {
  public componentForm: FormGroup;
  public submitted = false;

  protected constructor(formGroup: FormGroup) {
    this.componentForm = formGroup;
  }

  get f() {
    return this.componentForm.controls;
  }

  protected submitting() {
    this.submitted = true;
  }

  protected reset() {
    this.submitted = false;
    this.componentForm.reset();
  }

  onConfirm() {
    console.log('Confirm method to implement');
  }

  confirm() {
    this.submitting();
    if (this.componentForm.invalid) {
      return;
    }
    this.onConfirm();
    this.reset();
  }
}
