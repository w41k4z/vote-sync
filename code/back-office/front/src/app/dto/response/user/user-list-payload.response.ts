import { User } from '../../user';
import { PageModel } from '../page-model-payload.response';

export class UserListPayload {
  users: PageModel<User>;

  constructor(users: PageModel<User>) {
    this.users = users;
  }
}
