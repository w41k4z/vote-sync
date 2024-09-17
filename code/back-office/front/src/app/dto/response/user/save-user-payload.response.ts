import { User } from '../../user';

export class SaveUserPayload {
  user: User;

  constructor(user: User) {
    this.user = user;
  }
}
