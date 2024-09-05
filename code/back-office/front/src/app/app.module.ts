import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LogInComponent } from './views/log-in/log-in.component';
import { FullPageLayoutComponent } from './layouts/full-page-layout/full-page-layout.component';
import { AppLayoutComponent } from './layouts/app-layout/app-layout.component';
import { PageNotFoundComponent } from './views/page-not-found/page-not-found.component';
import { ReactiveFormsModule } from '@angular/forms';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { jwtInterceptor } from './filter/interceptors';
import { DashboardComponent } from './views/dashboard/dashboard.component';
import { TestComponent } from './views/test/test.component';

@NgModule({
  declarations: [
    AppComponent,
    LogInComponent,
    FullPageLayoutComponent,
    AppLayoutComponent,
    PageNotFoundComponent,
    DashboardComponent,
    TestComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, ReactiveFormsModule],
  providers: [provideHttpClient(withInterceptors([jwtInterceptor]))],
  bootstrap: [AppComponent],
})
export class AppModule {}
