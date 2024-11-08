import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElectionImportDialogComponent } from './election-import-dialog.component';

describe('ElectionImportDialogComponent', () => {
  let component: ElectionImportDialogComponent;
  let fixture: ComponentFixture<ElectionImportDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ElectionImportDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElectionImportDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
