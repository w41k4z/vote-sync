import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from '../../services/api/user/user.service';
import { UserStat } from '../../dto/user-stat';
import { NewUserRequest } from '../../dto/request/user/new-user.request';
import { User } from '../../dto/user';
import { Page } from '../../dto/response/page';
import { UpdateUserRequest } from '../../dto/request/user/update-user.request';
import { Pagination } from '../../pagination';
import { ImportUsersRequest } from '../../dto/request/user/import-users.request';

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
  pageSize: number = Pagination.DEFAULT_SIZE;
  filter: string | null = null;
  userTypeFilter: string = '*';

  displayProgressSpinner = false;

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
    return this.userService.createUser(newUserRequest).then((payload) => {
      if (payload) {
        this.updateUserListAndStats(payload.user);
      }
    });
  };

  updateUser = (userIndex: number, request: NewUserRequest) => {
    let updateRequest = new UpdateUserRequest(this.users[userIndex].id);
    updateRequest.name = request.name;
    updateRequest.firstName = request.firstName;
    updateRequest.roleId = request.roleId;
    updateRequest.contact = request.contact;
    updateRequest.identifier = request.identifier;
    updateRequest.password = request.password;
    return this.userService.updateUser(updateRequest).then((payload) => {
      if (payload) {
        this.users[userIndex] = payload.user;
      }
    });
  };

  deleteUser = (userId: number) => {
    return this.userService.deleteUser(userId);
  };

  importUsers = (request: ImportUsersRequest) => {
    this.displayProgressSpinner = true;
    try {
      return this.userService.importUsers(request);
    } catch (error) {
      throw error;
    } finally {
      this.displayProgressSpinner = false;
    }
  };

  private updateUserListAndStats(newUser: User) {
    this.users.unshift(newUser);
    for (let stat of this.stats) {
      if (stat.role === newUser.role.name) {
        stat.count++;
        break;
      }
    }
  }
}
