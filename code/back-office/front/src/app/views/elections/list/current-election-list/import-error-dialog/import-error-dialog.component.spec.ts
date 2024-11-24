import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportErrorDialogComponent } from './import-error-dialog.component';

describe('ImportErrorDialogComponent', () => {
  let component: ImportErrorDialogComponent;
  let fixture: ComponentFixture<ImportErrorDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ImportErrorDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImportErrorDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
