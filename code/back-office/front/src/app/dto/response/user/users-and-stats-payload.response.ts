import { User } from '../../user';
import { UserStat } from '../../user-stat';
import { PagedModel } from '../paged-model-payload.response';

export class UsersAndStatsPayload {
  users: PagedModel<User>;
  stats: UserStat[];

  constructor(users: PagedModel<User>, stats: UserStat[]) {
    this.users = users;
    this.stats = stats;
  }
}
