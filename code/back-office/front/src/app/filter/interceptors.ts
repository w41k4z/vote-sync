import { HttpEvent, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';
import { Paths } from '../paths';

export function jwtInterceptor(
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> {
  const token = inject(AuthService).accessToken;
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: 'Bearer ' + token,
      },
    });
  }
  return next(req);
}
