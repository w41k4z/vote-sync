import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from '../../services/api/user/user.service';
import { UserStat } from '../../dto/user-stat';
import { UserListPayload } from '../../dto/response/user/user-list-payload.response';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
})
export class UsersComponent {
  loading$!: Observable<Boolean>;
  error$!: Observable<string | null>;
  message$!: Observable<string | null>;
  userListPayload: UserListPayload | null = null;
  stats: UserStat[] = [];

  constructor(public userService: UserService) {
    this.loading$ = userService.loading$;
    this.error$ = userService.error$;
    this.message$ = userService.message$;

    this.userService.getUsersAndStats().then((usersAndStatsPayload) => {
      if (usersAndStatsPayload) {
        this.userListPayload = new UserListPayload(usersAndStatsPayload.users);
        this.stats = usersAndStatsPayload.stats;
      }
    });
  }
}
