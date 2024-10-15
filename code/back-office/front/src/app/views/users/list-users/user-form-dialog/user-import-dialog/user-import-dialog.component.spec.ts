import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserImportDialogComponent } from './user-import-dialog.component';

describe('UserImportDialogComponent', () => {
  let component: UserImportDialogComponent;
  let fixture: ComponentFixture<UserImportDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserImportDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserImportDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
