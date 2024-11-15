import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DistrictResultFilterComponent } from './district-result-filter.component';

describe('DistrictResultFilterComponent', () => {
  let component: DistrictResultFilterComponent;
  let fixture: ComponentFixture<DistrictResultFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DistrictResultFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DistrictResultFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
