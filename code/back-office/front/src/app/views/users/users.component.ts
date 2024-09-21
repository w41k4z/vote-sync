import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from '../../services/api/user/user.service';
import { UserStat } from '../../dto/user-stat';
import { NewUserRequest } from '../../dto/request/new-user.request';
import { User } from '../../dto/user';
import { Page } from '../../dto/response/page';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
})
export class UsersComponent {
  loading$!: Observable<Boolean>;
  error$!: Observable<string | null>;
  message$!: Observable<string | null>;
  page: Page | null = null;
  users: User[] = [];
  stats: UserStat[] = [];

  constructor(public userService: UserService) {
    this.loading$ = userService.loading$;
    this.error$ = userService.error$;
    this.message$ = userService.message$;

    this.userService.getUsersAndStats().then((usersAndStatsPayload) => {
      if (usersAndStatsPayload) {
        this.users = usersAndStatsPayload.users.content;
        this.page = usersAndStatsPayload.users.page;
        this.stats = usersAndStatsPayload.stats;
      }
    });
  }

  createNewUser = (newUserRequest: NewUserRequest) => {
    this.userService.createUser(newUserRequest).then((payload) => {
      if (payload) {
        this.updateUserListAndStats(payload.user);
      }
    });
  };

  private updateUserListAndStats(newUser: User) {
    this.users.push(newUser);
    for (let stat of this.stats) {
      if (stat.role === newUser.role.name) {
        stat.count++;
        break;
      }
    }
  }
}
