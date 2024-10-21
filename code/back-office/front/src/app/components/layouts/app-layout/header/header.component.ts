import { Component } from '@angular/core';
import { AuthService } from '../../../../services/auth/auth.service';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Paths } from '../../../../paths';
import { Title } from '@angular/platform-browser';
import { filter, map, mergeMap } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  pageTitle = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private titleService: Title
  ) {}

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
        this.pageTitle = data['title'] || 'Default Title';
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
