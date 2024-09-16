import { UserStat } from '../../user-stat';

export class UsersStatsPayload {
  stats: UserStat[];

  constructor(stats: UserStat[]) {
    this.stats = stats;
  }
}
