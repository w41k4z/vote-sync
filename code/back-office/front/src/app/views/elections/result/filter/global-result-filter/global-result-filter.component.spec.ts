import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalResultFilterComponent } from './global-result-filter.component';

describe('GlobalResultFilterComponent', () => {
  let component: GlobalResultFilterComponent;
  let fixture: ComponentFixture<GlobalResultFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GlobalResultFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GlobalResultFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
