import { VotersStat } from '../../voters-stat';

export class VotersStatPayload {
  votersStats: VotersStat[];

  constructor(votersStats: VotersStat[]) {
    this.votersStats = votersStats;
  }
}
