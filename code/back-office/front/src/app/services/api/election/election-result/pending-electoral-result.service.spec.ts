import { TestBed } from '@angular/core/testing';

import { PendingElectoralResultService } from './pending-electoral-result.service';

describe('PendingElectoralResultService', () => {
  let service: PendingElectoralResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PendingElectoralResultService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
