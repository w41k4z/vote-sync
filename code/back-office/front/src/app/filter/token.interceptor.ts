import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandlerFn,
  HttpRequest,
} from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, from, Observable, switchMap, tap, throwError } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';
import { Paths } from '../paths';
import { Endpoints } from '../endpoints';

export function jwtInterceptor(
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> {
  const authService = inject(AuthService);
  const router = inject(Router);
  const token = authService.accessToken;
  let originalRequest = req;
  req = addAuthorizationHeaderIfNecessary(req, token);
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        return handle401Error(
          error,
          originalRequest,
          authService,
          next,
          router
        );
      }
      return throwError(() => error);
    })
  );
}

function addAuthorizationHeaderIfNecessary(
  req: HttpRequest<unknown>,
  token: string | null
): HttpRequest<unknown> {
  const isPublicRequest = Endpoints.publicEndpoints.some((endpoint) =>
    req.url.includes(endpoint)
  );

  if (token && !isPublicRequest) {
    req = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` },
    });
  }

  return req;
}

function handle401Error(
  error: HttpErrorResponse,
  originalRequest: HttpRequest<unknown>,
  authService: AuthService,
  next: HttpHandlerFn,
  router: Router
): Observable<HttpEvent<unknown>> {
  const payload = error.error?.payload;

  // If the error payload contains refreshTokenRequired, try to refresh the token
  if (payload?.refreshTokenRequired) {
    return refreshAccessTokenAndRetry(
      originalRequest,
      authService,
      next,
      router
    );
  }

  router.navigate([Paths.PERMISSION_DENIED]);
  return throwError(() => error);
}

function refreshAccessTokenAndRetry(
  originalRequest: HttpRequest<unknown>,
  authService: AuthService,
  next: HttpHandlerFn,
  router: Router
): Observable<HttpEvent<unknown>> {
  let tokenRefreshFailed = true;
  return from(authService.refreshAccessToken()).pipe(
    // If token refresh is successful, retry the original request
    switchMap(() => {
      tokenRefreshFailed = false;
      const newToken = authService.accessToken;

      const refreshedRequest = originalRequest.clone({
        setHeaders: { Authorization: `Bearer ${newToken}` },
      });

      return next(refreshedRequest);
    }),
    // If token refresh fails, rethrow the error
    catchError((refreshError) => {
      // Redirect to sign-in page if token refresh fails
      if (tokenRefreshFailed) {
        console.error('Failed to refresh token:', refreshError);
        router.navigate([Paths.SIGN_IN]);
      }
      return throwError(() => refreshError);
    })
  );
}
