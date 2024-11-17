import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CloseElectionDialogComponent } from './close-election-dialog.component';

describe('CloseElectionDialogComponent', () => {
  let component: CloseElectionDialogComponent;
  let fixture: ComponentFixture<CloseElectionDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CloseElectionDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CloseElectionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
