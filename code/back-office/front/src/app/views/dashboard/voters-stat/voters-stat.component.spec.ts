import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VotersStatComponent } from './voters-stat.component';

describe('VotersStatComponent', () => {
  let component: VotersStatComponent;
  let fixture: ComponentFixture<VotersStatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VotersStatComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VotersStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
