import { Component, Input } from '@angular/core';
import { Election } from '../../../../dto/election';
import { ElectionType } from '../../../../dto/election-type';

@Component({
  selector: 'app-current-election-list',
  templateUrl: './current-election-list.component.html',
  styleUrl: './current-election-list.component.scss',
})
export class CurrentElectionListComponent {
  @Input() currentElections!: Election[];
  @Input() openAddDialog!: () => void;
  @Input() onClotureElection!: (election: Election) => Promise<void>;
  @Input() routePathByElectionType!: (type: ElectionType) => string;
  isCloturing = false;

  clotureElection = async (election: Election) => {
    this.isCloturing = true;
    setTimeout(() => {
      this.onClotureElection(election);
      this.isCloturing = false;
    }, 2000);
  };
}
