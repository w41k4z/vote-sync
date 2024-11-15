import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FokontanyLocalResultFilterComponent } from './fokontany-local-result-filter.component';

describe('FokontanyLocalResultFilterComponent', () => {
  let component: FokontanyLocalResultFilterComponent;
  let fixture: ComponentFixture<FokontanyLocalResultFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FokontanyLocalResultFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FokontanyLocalResultFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
