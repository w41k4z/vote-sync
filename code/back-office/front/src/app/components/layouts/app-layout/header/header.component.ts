import { Component } from '@angular/core';
import { AuthService } from '../../../../services/auth/auth.service';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Paths } from '../../../../paths';
import { Title } from '@angular/platform-browser';
import { filter, map, mergeMap } from 'rxjs';
import { NotificationService } from '../../../../services/notification/notification.service';
import { AlertService } from '../../../../services/api/alert/alert.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  pageTitle = 'VoteSync';
  userInformation: string | null = null;
  userAuthority: string | null = null;
  alertsPath = Paths.ALERTS;
  alertsCount = 0;

  constructor(
    private alertService: AlertService,
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private titleService: Title
  ) {
    this.userInformation = this.authService.getUserInformation();
    this.userAuthority = this.authService.getUserPrivilege();
    this.alertService.getAlertsCount().then((payload) => {
      if (payload) {
        this.alertsCount = payload.alertsCount;
      }
    });
    this.notificationService.connect('/notification/alerts');
    this.notificationService.message$.subscribe((message) => {
      if (message) {
        const jsonBody = JSON.parse(message.body);
        this.alertsCount += jsonBody.alertsCount;
      }
    });
  }

  ngOnInit(): void {
    this.router.events
      .pipe(
        filter((event) => event instanceof NavigationEnd),
        map(() => this.activatedRoute),
        map((route) => {
          while (route.firstChild) {
            route = route.firstChild;
          }
          return route;
        }),
        mergeMap((route) => route.data)
      )
      .subscribe((data) => {
        this.pageTitle = data['title'] || 'VoteSync';
        this.titleService.setTitle(this.pageTitle); // Set browser tab title
      });
  }

  async logOut() {
    try {
      await this.authService.logOut();
    } catch (error) {
      console.log('Something went wrong when trying to log out.');
      throw error;
    } finally {
      this.router.navigate([Paths.SIGN_IN]);
    }
  }
}
