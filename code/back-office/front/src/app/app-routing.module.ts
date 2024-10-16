import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FullPageLayoutComponent } from './layouts/full-page-layout/full-page-layout.component';
import { LogInComponent } from './views/log-in/log-in.component';
import { PageNotFoundComponent } from './views/page-not-found/page-not-found.component';
import { Paths } from './paths';
import { authGuard } from './guards/auth.guard';
import { DashboardComponent } from './views/dashboard/dashboard.component';
import { signInPageGuard } from './guards/sign-in-page.guard';
import { Privileges } from './privileges';
import { permissionGuard } from './guards/permission.guard';
import { PermissionDeniedComponent } from './views/permission-denied/permission-denied.component';
import { HomeComponent } from './views/home/home.component';
import { AppLayoutComponent } from './layouts/app-layout/app-layout.component';
import { UsersComponent } from './views/users/users.component';
import { ElectionsComponent } from './views/elections/elections.component';
import { PollingStationComponent } from './views/polling-station/polling-station.component';
import { MunicipalResultComponent } from './views/elections/municipal-result/municipal-result.component';

const routes: Routes = [
  {
    path: 'page',
    component: FullPageLayoutComponent,
    children: [
      {
        path: 'log-in',
        canActivate: [signInPageGuard],
        component: LogInComponent,
        data: {
          title: 'Connexion',
        },
      },
      {
        path: 'permission-denied',
        component: PermissionDeniedComponent,
        data: {
          title: 'Permission refusée',
        },
      },
    ],
  },
  {
    path: 'app',
    canActivate: [authGuard],
    component: AppLayoutComponent,
    canActivateChild: [permissionGuard],
    children: [
      {
        path: '',
        component: HomeComponent,
        data: {
          title: 'Accueil',
        },
      },
      {
        path: 'dashboard',
        component: DashboardComponent,
        data: {
          requiredPrivileges: [Privileges.ADMIN],
          title: 'Tableau de bord',
        },
      },
      {
        path: 'users',
        component: UsersComponent,
        data: { requiredPrivileges: [Privileges.ADMIN], title: 'Utilisateurs' },
      },
      {
        path: 'polling-stations',
        component: PollingStationComponent,
        data: {
          requiredPrivileges: [Privileges.ADMIN],
          title: 'Bureaux de vote',
        },
      },
      {
        path: 'elections',
        component: ElectionsComponent,
        data: { requiredPrivileges: [Privileges.ADMIN], title: 'Elections' },
      },
      {
        path: 'elections/result/municipal/:electionId',
        component: MunicipalResultComponent,
        data: { requiredPrivileges: [Privileges.ADMIN], title: 'Résultats' },
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
