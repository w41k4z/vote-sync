import { TestBed } from '@angular/core/testing';

import { AdministrativeDivisionService } from './administrative-division.service';

describe('StatService', () => {
  let service: AdministrativeDivisionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdministrativeDivisionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
