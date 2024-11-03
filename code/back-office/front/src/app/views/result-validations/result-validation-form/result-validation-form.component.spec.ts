import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultValidationFormComponent } from './result-validation-form.component';

describe('ResultValidationFormComponent', () => {
  let component: ResultValidationFormComponent;
  let fixture: ComponentFixture<ResultValidationFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ResultValidationFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResultValidationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
