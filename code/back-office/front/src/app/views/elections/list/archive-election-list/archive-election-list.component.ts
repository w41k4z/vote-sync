import { Component, Input } from '@angular/core';
import { Election } from '../../../../dto/election';
import { ElectionType } from '../../../../dto/election-type';

@Component({
  selector: 'app-archive-election-list',
  templateUrl: './archive-election-list.component.html',
  styleUrl: './archive-election-list.component.scss',
})
export class ArchiveElectionListComponent {
  @Input() electionHistory!: Election[];
  @Input() routePathByElectionType!: (type: ElectionType) => string;
  @Input() isLoading!: Boolean | null;
}
