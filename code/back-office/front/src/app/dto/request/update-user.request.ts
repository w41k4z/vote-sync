import { NewUserRequest } from './new-user.request';

export class UpdateUserRequest extends NewUserRequest {
  id: number;

  constructor(id: number) {
    super();
    this.id = id;
  }
}
