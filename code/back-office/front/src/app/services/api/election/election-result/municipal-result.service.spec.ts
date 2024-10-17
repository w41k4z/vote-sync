import { TestBed } from '@angular/core/testing';

import { MunicipalResultService } from './municipal-result.service';

describe('MunicipalResultService', () => {
  let service: MunicipalResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MunicipalResultService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
