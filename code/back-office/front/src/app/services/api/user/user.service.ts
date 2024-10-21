import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { HttpClient } from '@angular/common/http';
import { UserListPayload } from '../../../dto/response/user/user-list-payload.response';
import { Endpoints } from '../../../endpoints';
import { UsersAndStatsPayload } from '../../../dto/response/user/users-and-stats-payload.response';
import { NewUserRequest } from '../../../dto/request/user/new-user.request';
import { SaveUserPayload } from '../../../dto/response/user/save-user-payload.response';
import { UpdateUserRequest } from '../../../dto/request/user/update-user.request';
import { ImportUsersRequest } from '../../../dto/request/user/import-users.request';

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
        `${Endpoints.USERS}/users-and-stats${strParam}`
      )
    ).payload;
  }

  async createUser(newUserRequest: NewUserRequest) {
    return (
      await this.postCall<SaveUserPayload>(Endpoints.USERS, newUserRequest)
    ).payload;
  }

  async updateUser(updateUserRequest: UpdateUserRequest) {
    return (
      await this.putCall<SaveUserPayload>(Endpoints.USERS, updateUserRequest)
    ).payload;
  }

  async deleteUser(userId: number) {
    return (await this.deleteCall(`${Endpoints.USERS}?userId=${userId}`))
      .payload;
  }

  async importUsers(importUsersRequest: ImportUsersRequest) {
    const formData = new FormData();
    if (!importUsersRequest.file || !importUsersRequest.roleId) {
      return;
    }
    formData.append('file', importUsersRequest.file);
    formData.append('roleId', importUsersRequest.roleId.toString());
    return (await this.postCall(`${Endpoints.USERS}/import`, formData)).payload;
  }
}
