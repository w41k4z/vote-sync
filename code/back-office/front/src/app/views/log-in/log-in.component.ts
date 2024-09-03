import { Component } from '@angular/core';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrl: './log-in.component.scss',
})
export class LogInComponent {
  passwordIsShown = false;
  eyeIcons = ['bi bi-eye', 'bi bi-eye-slash'];

  togglePassword() {
    this.passwordIsShown = !this.passwordIsShown;
  }
}
