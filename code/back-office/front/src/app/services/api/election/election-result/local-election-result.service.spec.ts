import { TestBed } from '@angular/core/testing';

import { LocalElectionResultService } from './local-election-result.service';

describe('MunicipalResultService', () => {
  let service: LocalElectionResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LocalElectionResultService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
