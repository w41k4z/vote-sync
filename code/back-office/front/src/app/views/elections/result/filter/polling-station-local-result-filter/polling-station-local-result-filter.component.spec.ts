import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PollingStationLocalResultFilterComponent } from './polling-station-local-result-filter.component';

describe('PollingStationLocalResultFilterComponent', () => {
  let component: PollingStationLocalResultFilterComponent;
  let fixture: ComponentFixture<PollingStationLocalResultFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PollingStationLocalResultFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PollingStationLocalResultFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
