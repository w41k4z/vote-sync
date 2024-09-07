import { Component } from '@angular/core';
import { Paths } from '../../paths';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrl: './page-not-found.component.scss',
})
export class PageNotFoundComponent {
  constructor(private router: Router) {}

  redirect() {
    this.router.navigate([Paths.HOME]);
  }
}
