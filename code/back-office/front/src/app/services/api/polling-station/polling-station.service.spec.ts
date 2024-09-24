import { TestBed } from '@angular/core/testing';

import { PollingStationService } from './polling-station.service';

describe('PollingStationService', () => {
  let service: PollingStationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PollingStationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
