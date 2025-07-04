import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PermissionDeniedComponent } from './permission-denied.component';

describe('PermissionDeniedComponent', () => {
  let component: PermissionDeniedComponent;
  let fixture: ComponentFixture<PermissionDeniedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PermissionDeniedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PermissionDeniedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
