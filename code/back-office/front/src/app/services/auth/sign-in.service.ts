import { Injectable } from '@angular/core';
import { ApiCallService } from '../api/api-call.service';
import { HttpClient } from '@angular/common/http';
import { AuthRequest } from '../../dto/auth.request';
import { ApiResponse } from '../../dto/api.response';

type SignInResponse = {
  accessToken: string;
  refreshToken: string;
};

@Injectable({
  providedIn: 'root',
})
export class SignInService extends ApiCallService {
  constructor(httpClient: HttpClient) {
    super(httpClient);
  }

  signIn(username: string, password: string) {
    const authRequest = new AuthRequest(username, password);
    let res: SignInResponse | undefined;
    this.postCall('auth/sign-in', authRequest).subscribe(
      (response: ApiResponse<SignInResponse>) => {
        res = response.payload;
        response.payload?.accessToken;
      }
    );
    return res;
  }
}
