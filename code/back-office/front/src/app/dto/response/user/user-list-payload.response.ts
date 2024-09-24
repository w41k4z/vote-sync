import { User } from '../../user';
import { PagedModel } from '../paged-model-payload.response';

export class UserListPayload {
  users: PagedModel<User>;

  constructor(users: PagedModel<User>) {
    this.users = users;
  }
}
