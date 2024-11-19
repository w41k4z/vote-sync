import { TestBed } from '@angular/core/testing';

import { LegislativeElectionResultService } from './legislative-election-result.service';

describe('LegislativeElectionResultService', () => {
  let service: LegislativeElectionResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LegislativeElectionResultService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
