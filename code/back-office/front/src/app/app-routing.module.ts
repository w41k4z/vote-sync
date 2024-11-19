import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FullPageLayoutComponent } from './components/layouts/full-page-layout/full-page-layout.component';
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
import { AppLayoutComponent } from './components/layouts/app-layout/app-layout.component';
import { UsersComponent } from './views/users/users.component';
import { ElectionsComponent } from './views/elections/elections.component';
import { PollingStationComponent } from './views/polling-station/polling-station.component';
import { LocalElectionResultComponent } from './views/elections/result/local-election-result/local-election-result.component';
import { ResultValidationsComponent } from './views/result-validations/result-validations.component';
import { ResultValidationFormComponent } from './views/result-validations/result-validation-form/result-validation-form.component';
import { AlertComponent } from './views/alert/alert.component';
import { LegislativeElectionResultComponent } from './views/elections/result/legislative-election-result/legislative-election-result.component';
import { PresidentialElectionResultComponent } from './views/elections/result/presidential-election-result/presidential-election-result.component';

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
        path: 'alerts',
        component: AlertComponent,
        data: {
          requiredPrivileges: Privileges.ALL,
          title: 'Alertes',
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
        data: {
          requiredPrivileges: [Privileges.ADMIN, Privileges.CCID],
          title: 'Elections',
        },
      },
      {
        path: 'elections/result/local/:electionId',
        component: LocalElectionResultComponent,
        data: { requiredPrivileges: [Privileges.ADMIN], title: 'Résultats' },
      },
      {
        path: 'elections/result/legislative/:electionId',
        component: LegislativeElectionResultComponent,
        data: { requiredPrivileges: [Privileges.ADMIN], title: 'Résultats' },
      },
      {
        path: 'elections/result/presidential/:electionId',
        component: PresidentialElectionResultComponent,
        data: { requiredPrivileges: [Privileges.ADMIN], title: 'Résultats' },
      },
      {
        path: 'result-validations',
        component: ResultValidationsComponent,
        data: {
          requiredPrivileges: [Privileges.ADMIN, Privileges.OPERATOR],
          title: 'Résultat en attente',
        },
      },
      {
        path: 'result-validations/form',
        component: ResultValidationFormComponent,
        data: {
          requiredPrivileges: [Privileges.ADMIN, Privileges.OPERATOR],
          title: 'Formulaire de validation',
        },
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
