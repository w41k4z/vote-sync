import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatDialogContent, MatDialogActions } from '@angular/material/dialog';

import { AppComponent } from './app.component';
import { LogInComponent } from './views/log-in/log-in.component';
import { FullPageLayoutComponent } from './layouts/full-page-layout/full-page-layout.component';
import { AppLayoutComponent } from './layouts/app-layout/app-layout.component';
import { PageNotFoundComponent } from './views/page-not-found/page-not-found.component';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { jwtInterceptor } from './filter/token.interceptor';
import { DashboardComponent } from './views/dashboard/dashboard.component';
import { PermissionDeniedComponent } from './views/permission-denied/permission-denied.component';
import { HomeComponent } from './views/home/home.component';
import { HeaderComponent } from './layouts/app-layout/header/header.component';
import { SideBarComponent } from './layouts/app-layout/side-bar/side-bar.component';
import { UsersComponent } from './views/users/users.component';
import { ListUsersComponent } from './views/users/list-users/list-users.component';
import { FormatRolePipe } from './pipes/format-role.pipe';
import { UsersStatComponent } from './views/users/users-stat/users-stat.component';
import { UserFormDialogComponent } from './views/users/list-users/user-form-dialog/user-form-dialog.component';
import { DeleteDialogComponent } from './components/delete-dialog/delete-dialog.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { UserFiltersComponent } from './views/users/list-users/user-filters/user-filters.component';
import { FormatNumberPipe } from './pipes/format-number.pipe';
import { ElectionsComponent } from './views/elections/elections.component';
import { PollingStationComponent } from './views/polling-station/polling-station.component';
import { FormatNamePipe } from './pipes/format-name.pipe';
import { ElectionResultComponent } from './views/elections/election-result/election-result.component';
import { UserImportDialogComponent } from './views/users/list-users/user-form-dialog/user-import-dialog/user-import-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    LogInComponent,
    FullPageLayoutComponent,
    AppLayoutComponent,
    PageNotFoundComponent,
    DashboardComponent,
    PermissionDeniedComponent,
    HomeComponent,
    HeaderComponent,
    SideBarComponent,
    UsersComponent,
    ListUsersComponent,
    UsersStatComponent,
    UserFormDialogComponent,
    DeleteDialogComponent,
    UserFiltersComponent,
    ElectionsComponent,
    PollingStationComponent,
    FormatRolePipe,
    FormatNumberPipe,
    FormatNamePipe,
    ElectionResultComponent,
    UserImportDialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogContent,
    MatDialogActions,
    MatInputModule,
    LeafletModule,
  ],
  providers: [
    provideHttpClient(withInterceptors([jwtInterceptor])),
    provideAnimationsAsync(),
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
