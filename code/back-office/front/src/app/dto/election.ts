import { ElectionType } from './election-type';

export class Election {
  id: number;
  type: ElectionType;
  name: string;
  startDate: string;
  endDate: string;
  pollingStationCount: number;
  totalPollingStationCount: number;
  status: number;

  constructor(
    id: number,
    type: ElectionType,
    name: string,
    startDate: string,
    endDate: string,
    pollingStationCount: number,
    totalPollingStationCount: number,
    status: number
  ) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.pollingStationCount = pollingStationCount;
    this.totalPollingStationCount = totalPollingStationCount;
    this.status = status;
  }
}
