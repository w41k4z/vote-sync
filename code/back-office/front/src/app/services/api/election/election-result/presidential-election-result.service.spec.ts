import { TestBed } from '@angular/core/testing';

import { PresidentialElectionResultService } from './presidential-election-result.service';

describe('PresidentialElectionResultService', () => {
  let service: PresidentialElectionResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PresidentialElectionResultService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
