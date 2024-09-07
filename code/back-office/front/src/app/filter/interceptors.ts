import {
  HttpErrorResponse,
  HttpEvent,
  HttpEventType,
  HttpHandlerFn,
  HttpRequest,
} from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';

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

// return next(req).pipe(
//   catchError((error: HttpErrorResponse) => {
//     if (error.status === 401) {
//       console.log('Ity ilay erreur');
//       console.log(error);
//     }
//     return throwError(() => error);
//   })
// );
