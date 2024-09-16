import { Privileges } from '../privileges';

export class Role {
  id: number;
  name: string;
  level: number;

  constructor(id: number, name: string, level: number) {
    this.id = id;
    this.name = name;
    this.level = level;
  }

  getName(): string {
    return this.name;
  }
}
