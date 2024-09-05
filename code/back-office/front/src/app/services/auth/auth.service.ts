import { Injectable } from '@angular/core';
import { SignInService } from './sign-in.service';
import { Router } from '@angular/router';
import { Paths } from '../../paths';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public accessToken: string | null = null;
  public refreshToken: string | null = null;

  constructor(public signInService: SignInService, public router: Router) {}

  logIn(username: string, password: string) {
    const res = this.signInService.signIn(username, password);
    if (res) {
      this.accessToken = res.accessToken;
      this.refreshToken = res.refreshToken;
      localStorage.setItem('accessToken', this.accessToken);
      localStorage.setItem('refreshToken', this.refreshToken);
      this.router.navigate([Paths.DASHBOARD]);
    }
  }

  logOut() {
    this.accessToken = null;
    this.refreshToken = null;
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    this.router.navigate([Paths.SIGN_IN]);
  }

  isActive() {
    return !!this.accessToken;
  }
}
