import { Component } from '@angular/core';
import { UserService } from '../../../services/api/user/user.service';
import { Observable } from 'rxjs';
import { User } from '../../../dto/user';
import { Role } from '../../../dto/role';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.scss',
})
export class ListUsersComponent {
  loading$!: Observable<Boolean>;
  error$!: Observable<string | null>;
  message$!: Observable<string | null>;
  users: User[] = [];

  constructor(private userService: UserService) {
    this.loading$ = userService.loading$;
    this.error$ = userService.error$;
    this.message$ = userService.message$;
  }

  ngOnInit(): void {
    this.userService.getUsers().then((listUserPayload) => {
      if (listUserPayload) {
        this.users = listUserPayload.users;
      }
    });
  }
}
