import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FullPageLayoutComponent } from './layouts/full-page-layout/full-page-layout.component';
import { LogInComponent } from './views/log-in/log-in.component';
import { PageNotFoundComponent } from './views/page-not-found/page-not-found.component';
import { Paths } from './paths';
import { authGuard } from './guards/auth.guard';
import { DashboardComponent } from './views/dashboard/dashboard.component';
import { TestComponent } from './views/test/test.component';
import { signInPageGuard } from './guards/sign-in-page.guard';

const routes: Routes = [
  {
    path: 'page',
    component: FullPageLayoutComponent,
    children: [
      {
        path: 'log-in',
        canActivate: [signInPageGuard],
        component: LogInComponent,
      },
    ],
  },
  {
    path: 'app',
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
      },
      {
        path: 'test',
        component: TestComponent,
      },
    ],
  },
  { path: '', redirectTo: Paths.SIGN_IN, pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
