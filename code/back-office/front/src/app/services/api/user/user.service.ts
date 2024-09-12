import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call.service';
import { HttpClient } from '@angular/common/http';
import { UserListPayload } from '../../../dto/response/user-list-payload.response';
import { Endpoints } from '../../../endpoints';

@Injectable({
  providedIn: 'root',
})
export class UserService extends ApiCallService {
  constructor(private http: HttpClient) {
    super(http);
  }

  async getUsers() {
    return (await this.getCall<UserListPayload>(Endpoints.USERS)).payload;
  }
}
