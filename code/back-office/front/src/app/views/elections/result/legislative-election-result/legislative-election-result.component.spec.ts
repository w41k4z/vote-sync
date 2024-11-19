import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LegislativeElectionResultComponent } from './legislative-election-result.component';

describe('LegislativeElectionResultComponent', () => {
  let component: LegislativeElectionResultComponent;
  let fixture: ComponentFixture<LegislativeElectionResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LegislativeElectionResultComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LegislativeElectionResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
