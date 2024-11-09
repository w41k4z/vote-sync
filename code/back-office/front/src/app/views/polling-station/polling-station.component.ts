import { Component } from '@angular/core';
import { PollingStationService } from '../../services/api/polling-station/polling-station.service';
import { PollingStation } from '../../dto/polling-station';
import { Observable } from 'rxjs';
import { Page } from '../../dto/response/page';
import { Pagination } from '../../pagination';
import { AdministrativeDivision } from '../../dto/administrative-division';
import { AdministrativeDivisionService } from '../../services/api/administrative-division/administrative-division.service';

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
  regionId: string = '*';
  districtId: string = '*';
  communeId: string = '*';
  fokontanyId: string = '*';
  page: Page | null = null;
  pageSize: number = Pagination.DEFAULT_SIZE;

  constructor(
    private pollingStationService: PollingStationService,
    private administrativeDivisionService: AdministrativeDivisionService
  ) {
    this.loading$ = pollingStationService.loading$;
    this.error$ = pollingStationService.error$;
    this.message$ = pollingStationService.message$;
    this.filter();
  }

  filterByRegion() {
    if (!this.regionId || this.regionId === '*') {
      this.districts = [];
      this.communes = [];
      this.fokontany = [];
      return;
    }
    this.administrativeDivisionService
      .getDistrictsByRegionId(parseInt(this.regionId))
      .then((payload) => {
        if (payload) {
          this.districts = payload.administrativeDivisions;
        }
      });
  }

  filterByDistrict() {
    if (!this.districtId || this.districtId === '*') {
      this.communes = [];
      this.fokontany = [];
      return;
    }
    this.administrativeDivisionService
      .getCommunesByDistrictId(parseInt(this.districtId))
      .then((payload) => {
        if (payload) {
          this.communes = payload.administrativeDivisions;
        }
      });
  }

  filterByCommune() {
    if (!this.communeId || this.communeId === '*') {
      this.fokontany = [];
      return;
    }
    this.administrativeDivisionService
      .getFokontanyByCommuneId(parseInt(this.communeId))
      .then((payload) => {
        if (payload) {
          this.fokontany = payload.administrativeDivisions;
        }
      });
  }

  filter(page: number = 0) {
    this.pollingStationService
      .getPollingStations(
        page,
        this.pageSize,
        this.regionId,
        this.districtId,
        this.communeId,
        this.fokontanyId
      )
      .then((payload) => {
        if (payload) {
          this.pollingStations = payload.pollingStations.content;
          this.page = payload.pollingStations.page;
          this.regions = payload.regions;
        }
      });
  }

  reinitialize() {
    this.regionId = '*';
    this.districtId = '*';
    this.communeId = '*';
    this.fokontanyId = '*';
    this.filter();
  }

  nextPage = () => {
    if (this.page) {
      if (this.page.number + 1 < this.page.totalPages) {
        this.filter(this.page.number + 1);
      }
    }
  };

  previousPage = () => {
    if (this.page) {
      if (this.page.number - 1 >= 0) {
        this.filter(this.page.number - 1);
      }
    }
  };

  async assignOperators() {
    await this.pollingStationService.assignOperators();
    this.filter(this.page?.number);
  }
}
