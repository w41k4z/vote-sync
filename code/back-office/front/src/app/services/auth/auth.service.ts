import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Paths } from '../../paths';
import { ApiCallService } from '../api/api-call.service';
import { HttpClient } from '@angular/common/http';
import { AuthRequest } from '../../dto/request/auth.request';
import { SignInResponse } from '../../dto/response/sign-in.response';

@Injectable({
  providedIn: 'root',
})
export class AuthService extends ApiCallService {
  public accessToken: string | null = null;
  public refreshToken: string | null = null;

  constructor(httpClient: HttpClient, private router: Router) {
    super(httpClient);
    if (localStorage.getItem('accessToken')) {
      this.accessToken = localStorage.getItem('accessToken');
    }
    if (localStorage.getItem('refreshToken')) {
      this.refreshToken = localStorage.getItem('refreshToken');
    }
  }

  async logIn(username: string, password: string) {
    const authRequest = new AuthRequest(username, password);
    const res = await this.postCall<SignInResponse>(
      'auth/sign-in',
      authRequest
    );
    if (res && res.payload) {
      this.accessToken = res.payload.accessToken;
      this.refreshToken = res.payload.refreshToken;
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
