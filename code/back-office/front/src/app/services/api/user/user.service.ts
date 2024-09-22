import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { HttpClient } from '@angular/common/http';
import { UserListPayload } from '../../../dto/response/user/user-list-payload.response';
import { Endpoints } from '../../../endpoints';
import { UsersAndStatsPayload } from '../../../dto/response/user/users-and-stats-payload.response';
import { NewUserRequest } from '../../../dto/request/new-user.request';
import { SaveUserPayload } from '../../../dto/response/user/save-user-payload.response';

@Injectable({
  providedIn: 'root',
})
export class UserService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getUsers(
    filter: string | null,
    userTypeFilter: string,
    page: number,
    size: number
  ) {
    let params: string[] = [];
    params.push(`page=${page}`);
    params.push(`size=${size}`);
    if (filter) {
      params.push(`filter=${filter}`);
    }
    if (userTypeFilter != '*') {
      params.push(`userTypeFilter=${userTypeFilter}`);
    }
    let strParam = '';
    if (params.length > 0) {
      strParam = '?';
      for (let each of params) {
        strParam += each + '&';
      }
      strParam = strParam.slice(0, strParam.length - 1);
    }
    return (
      await this.getCall<UserListPayload>(`${Endpoints.USERS}${strParam}`)
    ).payload;
  }

  async getUsersAndStats(filter: string | null, userTypeFilter: string) {
    let params: string[] = [];
    if (filter) {
      params.push(`filter=${filter}`);
    }
    if (userTypeFilter != '*') {
      params.push(`userTypeFilter=${userTypeFilter}`);
    }
    let strParam = '';
    if (params.length > 0) {
      strParam = '?';
      for (let each of params) {
        strParam += each + '&';
      }
      strParam = strParam.slice(0, strParam.length - 1);
    }
    return (
      await this.getCall<UsersAndStatsPayload>(
        `${Endpoints.USERS_AND_STATS}${strParam}`
      )
    ).payload;
  }

  async createUser(newUserRequest: NewUserRequest) {
    return (
      await this.postCall<SaveUserPayload>(Endpoints.USERS, newUserRequest)
    ).payload;
  }
}
