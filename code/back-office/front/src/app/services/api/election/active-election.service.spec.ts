import { TestBed } from '@angular/core/testing';

import { ActiveElectionService } from './active-election.service';

describe('ActiveElectionService', () => {
  let service: ActiveElectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ActiveElectionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
