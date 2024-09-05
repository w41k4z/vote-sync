import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { signInPageGuard } from './sign-in-page.guard';

describe('loggedOutGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
    TestBed.runInInjectionContext(() => signInPageGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
