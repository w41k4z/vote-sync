import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArchiveElectionListComponent } from './archive-election-list.component';

describe('ArchiveElectionListComponent', () => {
  let component: ArchiveElectionListComponent;
  let fixture: ComponentFixture<ArchiveElectionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ArchiveElectionListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArchiveElectionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
