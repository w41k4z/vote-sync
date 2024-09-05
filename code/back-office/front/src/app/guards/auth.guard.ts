import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  if (inject(AuthService).isActive()) {
    return true;
  }
  inject(Router).navigate(['/']);
  return false;
};
