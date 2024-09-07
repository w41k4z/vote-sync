import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { FormContainerComponent } from '../../components/form-container/form-container.component';
import { Router } from '@angular/router';
import { Paths } from '../../paths';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrl: './log-in.component.scss',
})
export class LogInComponent extends FormContainerComponent {
  passwordIsShown = false;
  eyeIcons = ['bi bi-eye', 'bi bi-eye-slash'];
  loading$!: Observable<Boolean>;
  error$!: Observable<string | null>;

  constructor(private authService: AuthService, private router: Router) {
    const signInForm = new FormGroup({
      identifier: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
    super(signInForm);
    this.loading$ = authService.loading$;
    this.error$ = authService.error$;
  }

  togglePassword() {
    this.passwordIsShown = !this.passwordIsShown;
  }

  async submit() {
    this.submitting();
    if (this.componentForm.invalid) {
      return;
    }
    const identifier = this.componentForm.value.identifier
      ? this.componentForm.value.identifier
      : '';
    const password = this.componentForm.value.password
      ? this.componentForm.value.password
      : '';
    await this.authService.authenticate(identifier, password);
    this.router.navigate([Paths.HOME]);
    this.reset();
  }
}
