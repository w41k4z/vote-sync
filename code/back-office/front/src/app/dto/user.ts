import { Role } from './role';

export class User {
  id: number;
  identifier: string;
  role: Role;
  name: string;
  firstName: string;
  contact: string;
  password: string;

  constructor(
    id: number,
    identifier: string,
    role: Role,
    name: string,
    firstName: string,
    contact: string,
    password: string
  ) {
    this.id = id;
    this.identifier = identifier;
    this.role = role;
    this.name = name;
    this.firstName = firstName;
    this.contact = contact;
    this.password = password;
  }
}
