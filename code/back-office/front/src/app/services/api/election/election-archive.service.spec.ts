import { TestBed } from '@angular/core/testing';

import { ElectionArchiveService } from './election-archive.service';

describe('ElectionArchiveService', () => {
  let service: ElectionArchiveService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElectionArchiveService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
