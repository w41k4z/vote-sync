import { User } from '../../user';
import { UserStat } from '../../user-stat';
import { PageModel } from '../page-model-payload.response';

export class UsersAndStatsPayload {
  users: PageModel<User>;
  stats: UserStat[];

  constructor(users: PageModel<User>, stats: UserStat[]) {
    this.users = users;
    this.stats = stats;
  }
}
