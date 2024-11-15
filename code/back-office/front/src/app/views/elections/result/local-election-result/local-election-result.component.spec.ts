import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MunicipalResultComponent } from './municipal-result.component';

describe('MunicipalResultComponent', () => {
  let component: MunicipalResultComponent;
  let fixture: ComponentFixture<MunicipalResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MunicipalResultComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MunicipalResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
