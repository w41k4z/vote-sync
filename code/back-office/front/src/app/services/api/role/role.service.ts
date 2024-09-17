import { Injectable } from '@angular/core';
import { ApiCallService } from '../api-call';
import { HttpClient } from '@angular/common/http';
import { RoleListPayload } from '../../../dto/response/user/role-list-payload.response';
import { Endpoints } from '../../../endpoints';

@Injectable({
  providedIn: 'root',
})
export class RoleService extends ApiCallService {
  constructor(http: HttpClient) {
    super(http);
  }

  async getAllRoles() {
    return (await this.getCall<RoleListPayload>(Endpoints.ROLES)).payload;
  }
}
