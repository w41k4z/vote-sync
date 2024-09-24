import { Component } from '@angular/core';
import { PollingStationService } from '../../services/api/polling-station/polling-station.service';
import { PollingStation } from '../../dto/polling-station';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-polling-station',
  templateUrl: './polling-station.component.html',
  styleUrl: './polling-station.component.scss',
})
export class PollingStationComponent {
  loading$: Observable<Boolean>;
  error$: Observable<string | null>;
  message$: Observable<string | null>;
  pollingStations: PollingStation[] = [];

  constructor(private pollingStationService: PollingStationService) {
    this.loading$ = pollingStationService.loading$;
    this.error$ = pollingStationService.error$;
    this.message$ = pollingStationService.message$;
    this.pollingStationService.getPollingStations().then((payload) => {
      if (payload) {
        this.pollingStations = payload.pollingStations;
      }
    });
  }
}
