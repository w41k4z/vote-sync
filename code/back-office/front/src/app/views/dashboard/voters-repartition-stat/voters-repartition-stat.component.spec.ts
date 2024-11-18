import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VotersRepartitionStatComponent } from './voters-repartition-stat.component';

describe('VotersRepartitionStatComponent', () => {
  let component: VotersRepartitionStatComponent;
  let fixture: ComponentFixture<VotersRepartitionStatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VotersRepartitionStatComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VotersRepartitionStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
