import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MunicipalResultFilterComponent } from './municipal-result-filter.component';

describe('MunicipalResultFilterComponent', () => {
  let component: MunicipalResultFilterComponent;
  let fixture: ComponentFixture<MunicipalResultFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MunicipalResultFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MunicipalResultFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
