import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultWithNoFilterComponent } from './result-with-no-filter.component';

describe('ResultWithNoFilterComponent', () => {
  let component: ResultWithNoFilterComponent;
  let fixture: ComponentFixture<ResultWithNoFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ResultWithNoFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResultWithNoFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
