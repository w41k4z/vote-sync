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
  users: User[] = [];
  stats: UserStat[] = [];
  page: Page | null = null;
  pageSize: number = 5;
  filter: string | null = null;
  userTypeFilter: string = '*';

  constructor(public userService: UserService) {
    this.loading$ = userService.loading$;
    this.error$ = userService.error$;
    this.message$ = userService.message$;

    this.userService
      .getUsersAndStats(this.filter, this.userTypeFilter)
      .then((usersAndStatsPayload) => {
        if (usersAndStatsPayload) {
          this.users = usersAndStatsPayload.users.content;
          this.page = usersAndStatsPayload.users.page;
          this.stats = usersAndStatsPayload.stats;
        }
      });
  }

  filterUsers = (
    filter: string | null,
    userTypeFilter: string,
    page: number
  ) => {
    this.userService
      .getUsers(filter, userTypeFilter, page, this.pageSize)
      .then((payload) => {
        if (payload) {
          this.users = payload.users.content;
          this.page = payload.users.page;
        }
      });
    this.filter = filter;
    this.userTypeFilter = userTypeFilter;
  };

  nextPage = () => {
    if (this.page) {
      if (this.page.number + 1 < this.page.totalPages) {
        this.filterUsers(
          this.filter,
          this.userTypeFilter,
          this.page.number + 1
        );
      }
    }
  };

  previousPage = () => {
    if (this.page) {
      if (this.page.number - 1 >= 0) {
        this.filterUsers(
          this.filter,
          this.userTypeFilter,
          this.page.number - 1
        );
      }
    }
  };

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
