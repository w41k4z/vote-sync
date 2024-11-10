import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatDialogContent, MatDialogActions } from '@angular/material/dialog';
import { CarouselModule } from 'primeng/carousel';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';

import { AppComponent } from './app.component';
import { LogInComponent } from './views/log-in/log-in.component';
import { FullPageLayoutComponent } from './components//layouts/full-page-layout/full-page-layout.component';
import { AppLayoutComponent } from './components//layouts/app-layout/app-layout.component';
import { PageNotFoundComponent } from './views/page-not-found/page-not-found.component';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { jwtInterceptor } from './filter/token.interceptor';
import { DashboardComponent } from './views/dashboard/dashboard.component';
import { PermissionDeniedComponent } from './views/permission-denied/permission-denied.component';
import { HomeComponent } from './views/home/home.component';
import { HeaderComponent } from './components/layouts/app-layout/header/header.component';
import { SideBarComponent } from './components//layouts/app-layout/side-bar/side-bar.component';
import { UsersComponent } from './views/users/users.component';
import { UserListComponent } from './views/users/user-list/user-list.component';
import { FormatRolePipe } from './pipes/format-role/format-role.pipe';
import { UsersStatComponent } from './views/users/users-stat/users-stat.component';
import { UserFormDialogComponent } from './views/users/user-list/user-form-dialog/user-form-dialog.component';
import { DeleteDialogComponent } from './components/delete-dialog/delete-dialog.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { UserFiltersComponent } from './views/users/user-list/user-filters/user-filters.component';
import { FormatNumberPipe } from './pipes/format-number/format-number.pipe';
import { ElectionsComponent } from './views/elections/elections.component';
import { PollingStationComponent } from './views/polling-station/polling-station.component';
import { FormatNamePipe } from './pipes/format-name/format-name.pipe';
import { UserImportDialogComponent } from './views/users/user-list/user-form-dialog/user-import-dialog/user-import-dialog.component';
import { ElectionDialogComponent } from './views/elections/election-dialog/election-dialog.component';
import { FormatPublicUrlPipe } from './pipes/format-public-url/format-public-url.pipe';
import { MunicipalResultComponent } from './views/elections/result/municipal-result/municipal-result.component';
import { MunicipalResultFilterComponent } from './views/elections/result/municipal-result/municipal-result-filter/municipal-result-filter.component';
import { ResultValidationsComponent } from './views/result-validations/result-validations.component';
import { ResultValidationFormComponent } from './views/result-validations/result-validation-form/result-validation-form.component';
import { CurrentElectionListComponent } from './views/elections/list/current-election-list/current-election-list.component';
import { ArchiveElectionListComponent } from './views/elections/list/archive-election-list/archive-election-list.component';
import { ElectionImportDialogComponent } from './views/elections/list/current-election-list/election-import-dialog/election-import-dialog.component';
import { AlertComponent } from './views/alert/alert.component';
import {
  BaseChartDirective,
  provideCharts,
  withDefaultRegisterables,
} from 'ng2-charts';

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
    UserListComponent,
    UsersStatComponent,
    UserFormDialogComponent,
    DeleteDialogComponent,
    UserFiltersComponent,
    ElectionsComponent,
    PollingStationComponent,
    FormatRolePipe,
    FormatNumberPipe,
    FormatNamePipe,
    UserImportDialogComponent,
    ElectionDialogComponent,
    FormatPublicUrlPipe,
    MunicipalResultComponent,
    MunicipalResultFilterComponent,
    ResultValidationsComponent,
    ResultValidationFormComponent,
    CurrentElectionListComponent,
    ArchiveElectionListComponent,
    ElectionImportDialogComponent,
    AlertComponent,
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
    CarouselModule,
    DialogModule,
    ButtonModule,
    BaseChartDirective,
  ],
  providers: [
    provideHttpClient(withInterceptors([jwtInterceptor])),
    provideAnimationsAsync(),
    provideCharts(withDefaultRegisterables()),
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
