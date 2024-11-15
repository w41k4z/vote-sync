import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PollingStationResultFilterComponent } from './polling-station-result-filter.component';

describe('PollingStationResultFilterComponent', () => {
  let component: PollingStationResultFilterComponent;
  let fixture: ComponentFixture<PollingStationResultFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PollingStationResultFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PollingStationResultFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
