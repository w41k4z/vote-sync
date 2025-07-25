import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersStatComponent } from './users-stat.component';

describe('UsersStatComponent', () => {
  let component: UsersStatComponent;
  let fixture: ComponentFixture<UsersStatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UsersStatComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
