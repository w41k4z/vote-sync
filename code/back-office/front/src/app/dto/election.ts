import { ElectionType } from './election-type';

export class Election {
  id: number;
  type: ElectionType;
  name: string;
  startDate: string;
  endDate: string;

  constructor(
    id: number,
    type: ElectionType,
    name: string,
    startDate: string,
    endDate: string
  ) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
