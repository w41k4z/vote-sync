import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Paths } from '../../paths';

@Component({
  selector: 'app-permission-denied',
  templateUrl: './permission-denied.component.html',
  styleUrl: './permission-denied.component.scss',
})
export class PermissionDeniedComponent {
  constructor(private router: Router) {}

  redirect() {
    this.router.navigate([Paths.HOME]);
  }
}
