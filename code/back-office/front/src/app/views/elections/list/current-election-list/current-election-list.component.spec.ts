import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentElectionListComponent } from './current-election-list.component';

describe('CurrentElectionListComponent', () => {
  let component: CurrentElectionListComponent;
  let fixture: ComponentFixture<CurrentElectionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CurrentElectionListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CurrentElectionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
