import { Role } from "../../role";

export class RoleListPayload {
    roles: Role[];

    constructor(roles: Role[]) {
        this.roles = roles;
    }
}