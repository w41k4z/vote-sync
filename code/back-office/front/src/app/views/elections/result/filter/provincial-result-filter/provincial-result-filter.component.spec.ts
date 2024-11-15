import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProvincialResultFilterComponent } from './provincial-result-filter.component';

describe('ProvincialResultFilterComponent', () => {
  let component: ProvincialResultFilterComponent;
  let fixture: ComponentFixture<ProvincialResultFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProvincialResultFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProvincialResultFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
