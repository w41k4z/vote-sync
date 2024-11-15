import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunalResultFilterComponent } from './communal-result-filter.component';

describe('CommunalResultFilterComponent', () => {
  let component: CommunalResultFilterComponent;
  let fixture: ComponentFixture<CommunalResultFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CommunalResultFilterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommunalResultFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
