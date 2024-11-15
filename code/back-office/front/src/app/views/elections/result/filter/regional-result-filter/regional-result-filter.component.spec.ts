import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegionalResultFilterComponent } from './regional-result-filter.component';

describe('RegionalResultFilterComponent', () => {
  let component: RegionalResultFilterComponent;
  let fixture: ComponentFixture<RegionalResultFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegionalResultFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegionalResultFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
