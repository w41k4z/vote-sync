import { inject } from '@angular/core';
import { CanActivateChildFn, Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { Paths } from '../paths';

export const permissionGuard: CanActivateChildFn = (childRoute, state) => {
  const requiredPrivileges = childRoute.data['requiredPrivileges'];
  if (!requiredPrivileges || !requiredPrivileges.length) {
    return true; // No role requirement, allow access
  }
  const userPrivilege = inject(AuthService).getUserPrivilege();
  if (requiredPrivileges.includes(userPrivilege)) {
    return true; // User has required role, allow access
  }
  inject(Router).navigate([Paths.PERMISSION_DENIED]);
  return false;
};
