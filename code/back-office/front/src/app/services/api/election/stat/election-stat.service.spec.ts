import { TestBed } from '@angular/core/testing';

import { ElectionStatService } from './election-stat.service';

describe('ElectionStatService', () => {
  let service: ElectionStatService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElectionStatService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
