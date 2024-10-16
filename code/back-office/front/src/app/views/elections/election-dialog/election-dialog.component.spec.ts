import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElectionDialogComponent } from './election-dialog.component';

describe('ElectionDialogComponent', () => {
  let component: ElectionDialogComponent;
  let fixture: ComponentFixture<ElectionDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ElectionDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElectionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
