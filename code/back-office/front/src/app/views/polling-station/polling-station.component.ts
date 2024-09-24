import { Component } from '@angular/core';
import { PollingStationService } from '../../services/api/polling-station/polling-station.service';
import { PollingStation } from '../../dto/polling-station';
import { Observable } from 'rxjs';
import { Page } from '../../dto/response/page';
import { Pagination } from '../../pagination';
import { AdministrativeDivision } from '../../dto/administrative-division';

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
  regions: AdministrativeDivision[] = [];
  districts: AdministrativeDivision[] = [];
  communes: AdministrativeDivision[] = [];
  fokontany: AdministrativeDivision[] = [];
  page: Page | null = null;
  pageSize: number = Pagination.DEFAULT_SIZE;

  constructor(private pollingStationService: PollingStationService) {
    this.loading$ = pollingStationService.loading$;
    this.error$ = pollingStationService.error$;
    this.message$ = pollingStationService.message$;
    this.pollingStationService
      .getPollingStations(0, this.pageSize)
      .then((payload) => {
        if (payload) {
          this.pollingStations = payload.pollingStations.content;
          this.page = payload.pollingStations.page;
          this.regions = payload.regions;
        }
      });
  }
}
