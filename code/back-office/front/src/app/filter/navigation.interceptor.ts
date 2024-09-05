import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';
import { Paths } from '../paths';

@Injectable({
  providedIn: 'root',
})
export class NavigationInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (req.url.includes('log-in') && this.authService.isActive()) {
      this.authService.router.navigate([Paths.DASHBOARD]);
    }

    return next.handle(req);
  }
}
