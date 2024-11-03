import { AbstractControl, ValidationErrors } from '@angular/forms';

export class NumberValidators {
  public static positiveNumberValidator(
    control: AbstractControl
  ): ValidationErrors | null {
    const value = parseFloat(control.value);
    if (!isNaN(value)) {
      if (value <= 0) {
        return { positiveNumber: true };
      }
      return null;
    }
    return { positiveNumber: true };
  }
}
