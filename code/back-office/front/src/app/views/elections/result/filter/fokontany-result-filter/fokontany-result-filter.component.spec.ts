import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FokontanyResultFilterComponent } from './fokontany-result-filter.component';

describe('FokontanyResultFilterComponent', () => {
  let component: FokontanyResultFilterComponent;
  let fixture: ComponentFixture<FokontanyResultFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FokontanyResultFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FokontanyResultFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
