import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { AdministrativeDivision } from '../../dto/administrative-division';
import { PollingStation } from '../../dto/polling-station';
import { Page } from '../../dto/response/page';
import { Pagination } from '../../pagination';
import { AdministrativeDivisionService } from '../../services/api/administrative-division/administrative-division.service';
import { AlertService } from '../../services/api/alert/alert.service';
import { Alert } from '../../dto/alert';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrl: './alert.component.scss',
})
export class AlertComponent {
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

  alerts: Alert[] = [];

  constructor(
    private alertService: AlertService,
    private administrativeDivisionService: AdministrativeDivisionService
  ) {
    this.loading$ = alertService.loading$;
    this.error$ = alertService.error$;
    this.message$ = alertService.message$;
    this.filter();
    this.administrativeDivisionService.getRegions().then((payload) => {
      if (payload) {
        this.regions = payload.administrativeDivisions;
      }
    });
  }

  filter(page: number = 0) {
    this.alertService
      .getCurrentAlerts(
        page,
        this.pageSize,
        this.regionId,
        this.districtId,
        this.communeId,
        this.fokontanyId
      )
      .then((payload) => {
        if (payload) {
          this.alerts = payload.alerts.content;
          this.page = payload.alerts.page;
        }
      });
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
}
