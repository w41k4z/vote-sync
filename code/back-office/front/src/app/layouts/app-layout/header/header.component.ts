import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth/auth.service';
import { Router } from '@angular/router';
import { Paths } from '../../../paths';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  title = document.title;

  constructor(private authService: AuthService, private router: Router) {}

  async logOut() {
    try {
      await this.authService.logOut();
    } catch (error) {
      console.log('Something went wrong when trying to log out.');
      throw error;
    } finally {
      this.router.navigate([Paths.SIGN_IN]);
    }
  }
}
