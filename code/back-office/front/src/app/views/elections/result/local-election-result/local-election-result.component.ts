import { Component } from '@angular/core';
import { Election } from '../../../../dto/election';
import { ElectoralResult } from '../../../../dto/electoral-result';
import { ActivatedRoute } from '@angular/router';
import { LocalElectionResultService } from '../../../../services/api/election/election-result/local-election-result.service';
import { ElectionService } from '../../../../services/api/election/election.service';
import { Page } from '../../../../dto/response/page';
import { Observable } from 'rxjs';
import { AdministrativeDivision } from '../../../../dto/administrative-division';
import { PollingStation } from '../../../../dto/polling-station';
import { AdministrativeDivisionService } from '../../../../services/api/administrative-division/administrative-division.service';

@Component({
  selector: 'app-local-election-result',
  templateUrl: './local-election-result.component.html',
  styleUrl: './local-election-result.component.scss',
})
export class LocalElectionResultComponent {
  loading$: Observable<Boolean>;
  error$: Observable<string | null>;
  message$: Observable<string | null>;
  results: string[] = ['Par bureau de vote', 'Par fokontany', 'Par commune'];
  current = 0;
  election: Election | null = null;
  electoralResults: ElectoralResult[] = [];
  page: Page | null = null;

  pollingStations: PollingStation[] = [];
  regions: AdministrativeDivision[] = [];
  districts: AdministrativeDivision[] = [];
  municipalities: AdministrativeDivision[] = [];
  fokontany: AdministrativeDivision[] = [];

  regionId: string = '*';
  districtId: string = '*';
  municipalityId: string = '*';
  fokontanyId: string = '*';

  constructor(
    private route: ActivatedRoute,
    private electionService: ElectionService,
    private electionResultService: LocalElectionResultService,
    private administrativeDivisionService: AdministrativeDivisionService
  ) {
    this.loading$ = electionResultService.loading$;
    this.error$ = electionResultService.error$;
    this.message$ = electionResultService.message$;
    const electionId = this.route.snapshot.paramMap.get('electionId');
    if (electionId) {
      this.electionService.getElection(electionId).then((payload) => {
        if (payload) {
          this.election = payload.election;
          this.filter(
            0,
            this.regionId,
            this.districtId,
            this.municipalityId,
            this.fokontanyId
          );
        }
      });
      this.administrativeDivisionService.getRegions().then((payload) => {
        if (payload) {
          this.regions = payload.administrativeDivisions;
        }
      });
    }
  }

  handleResultChange(index: number) {
    this.current = index;
    this.regionId = '*';
    this.districtId = '*';
    this.municipalityId = '*';
    this.fokontanyId = '*';
    this.filter(
      0,
      this.regionId,
      this.districtId,
      this.municipalityId,
      this.fokontanyId
    );
  }

  filter = (
    page: number = 0,
    regionId: string,
    districtId: string,
    municipalityId: string,
    fokontanyId: string
  ) => {
    this.regionId = regionId;
    this.districtId = districtId;
    this.municipalityId = municipalityId;
    this.fokontanyId = fokontanyId;
    this.getElectoralResults(page);
  };

  getElectoralResults(page: number) {
    const electionId = this.route.snapshot.paramMap.get('electionId');
    if (electionId) {
      switch (this.current) {
        case 0:
          this.electionResultService
            .getPollingStationResults(
              page,
              1,
              electionId,
              this.regionId,
              this.districtId,
              this.municipalityId,
              this.fokontanyId
            )
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults.content;
                this.page = payload.electoralResults.page;
              }
            });
          break;
        case 1:
          this.electionResultService
            .getFokontanyResults(page, 1, electionId)
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults.content;
                this.page = payload.electoralResults.page;
              }
            });
          break;
        case 2:
          this.electionResultService
            .getMunicipalResults(page, 1, electionId)
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults.content;
                this.page = payload.electoralResults.page;
              }
            });
          break;
        default:
          break;
      }
    }
  }

  nextPage = () => {
    if (this.page) {
      if (this.page.number + 1 < this.page.totalPages) {
        this.filter(
          this.page.number + 1,
          this.regionId,
          this.districtId,
          this.municipalityId,
          this.fokontanyId
        );
      }
    }
  };

  previousPage = () => {
    if (this.page) {
      if (this.page.number - 1 >= 0) {
        this.filter(
          this.page.number - 1,
          this.regionId,
          this.districtId,
          this.municipalityId,
          this.fokontanyId
        );
      }
    }
  };

  filterByRegion = (regionId: string) => {
    if (!regionId || regionId === '*') {
      this.districts = [];
      this.municipalities = [];
      this.fokontany = [];
      return;
    }
    this.administrativeDivisionService
      .getMunicipalityDistrictsByRegionId(parseInt(regionId))
      .then((payload) => {
        if (payload) {
          this.districts = payload.administrativeDivisions;
        }
      });
  };
  filterByDistrict = (districtId: string) => {
    if (!districtId || districtId === '*') {
      this.municipalities = [];
      this.fokontany = [];
      return;
    }
    this.administrativeDivisionService
      .getMunicipalitiesByDistrictId(parseInt(districtId))
      .then((payload) => {
        if (payload) {
          this.municipalities = payload.administrativeDivisions;
        }
      });
  };
  filterByMunicipality = (municipalityId: string) => {
    if (!municipalityId || municipalityId === '*') {
      this.fokontany = [];
      return;
    }
    this.administrativeDivisionService
      .getFokontanyByMunicipalityId(parseInt(municipalityId))
      .then((payload) => {
        if (payload) {
          this.fokontany = payload.administrativeDivisions;
        }
      });
  };
}
