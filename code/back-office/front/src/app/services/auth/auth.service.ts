import { Injectable } from '@angular/core';
import { ApiCallService } from '../api/api-call.service';
import { HttpClient } from '@angular/common/http';
import { AuthRequest } from '../../dto/request/auth.request';
import { SignInPayloadResponse } from '../../dto/response/sign-in-payload.response';
import { jwtDecode } from 'jwt-decode';
import { AppJwt } from '../../dto/app.jwt';
import { RefreshTokenPayloadResponse } from '../../dto/response/refresh-token-payload.response';
import { Endpoints } from '../../endpoints';

@Injectable({
  providedIn: 'root',
})
export class AuthService extends ApiCallService {
  public accessToken: string | null = null;
  public refreshToken: string | null = null;

  isAuthenticated() {
    return !!this.accessToken;
  }

  getUserPrivilege() {
    if (!this.accessToken) {
      return null;
    }
    const decoded: AppJwt = jwtDecode(this.accessToken);
    return decoded.authority;
  }

  constructor(httpClient: HttpClient) {
    super(httpClient);
    if (localStorage.getItem('accessToken')) {
      this.accessToken = localStorage.getItem('accessToken');
    }
    if (localStorage.getItem('refreshToken')) {
      this.refreshToken = localStorage.getItem('refreshToken');
    }
  }

  async authenticate(username: string, password: string) {
    const authRequest = new AuthRequest(username, password);
    const res = await this.postCall<SignInPayloadResponse>(
      Endpoints.SIGN_IN,
      authRequest
    );
    if (res && res.payload) {
      this.accessToken = res.payload.accessToken;
      this.refreshToken = res.payload.refreshToken;
      localStorage.setItem('accessToken', this.accessToken);
      localStorage.setItem('refreshToken', this.refreshToken);
    }
  }

  async refreshAccessToken() {
    if (!this.refreshToken) {
      await this.logOut();
      throw new Error('Refresh token not found');
    }
    const res = await this.postCall<RefreshTokenPayloadResponse>(
      Endpoints.REFRESH_TOKEN,
      {
        refreshToken: this.refreshToken,
      }
    );
    if (res && res.payload) {
      this.accessToken = res.payload.accessToken;
      localStorage.setItem('accessToken', this.accessToken);
    }
  }

  async logOut() {
    await this.getCall(Endpoints.SIGN_OUT);
    this.accessToken = null;
    this.refreshToken = null;
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }
}
