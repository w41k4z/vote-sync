import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PresidentialElectionResultComponent } from './presidential-election-result.component';

describe('PresidentialElectionResultComponent', () => {
  let component: PresidentialElectionResultComponent;
  let fixture: ComponentFixture<PresidentialElectionResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PresidentialElectionResultComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PresidentialElectionResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
