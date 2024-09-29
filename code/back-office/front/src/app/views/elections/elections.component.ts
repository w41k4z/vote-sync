import { Component } from '@angular/core';
import { ElectionService } from '../../services/api/election/election.service';
import { Election } from '../../dto/election';

@Component({
  selector: 'app-elections',
  templateUrl: './elections.component.html',
  styleUrl: './elections.component.scss',
})
export class ElectionsComponent {
  currentElections: Election[] = [];
  electionHistory: Election[] = [];

  constructor(private electionService: ElectionService) {
    this.electionService.getCurrentElections().then((payload) => {
      if (payload) {
        this.currentElections = payload.elections;
        console.log(payload.elections);
        console.log(this.currentElections);
      }
    });
  }
}
