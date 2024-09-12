import { User } from '../user';

export class UserListPayload {
  users: User[];

  constructor(users: User[]) {
    this.users = users;
  }
}
