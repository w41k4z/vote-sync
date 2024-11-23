import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { AdministrativeDivision } from '../../../../dto/administrative-division';
import { Election } from '../../../../dto/election';
import { ElectoralResult } from '../../../../dto/electoral-result';
import { PollingStation } from '../../../../dto/polling-station';
import { Page } from '../../../../dto/response/page';
import { AdministrativeDivisionService } from '../../../../services/api/administrative-division/administrative-division.service';
import { ElectionService } from '../../../../services/api/election/election.service';
import { PresidentialElectionResultService } from '../../../../services/api/election/election-result/presidential-election-result.service';

@Component({
  selector: 'app-presidential-election-result',
  templateUrl: './presidential-election-result.component.html',
  styleUrl: './presidential-election-result.component.scss',
})
export class PresidentialElectionResultComponent {
  loading$: Observable<Boolean>;
  error$: Observable<string | null>;
  message$: Observable<string | null>;
  results: string[] = [
    'Par bureau de vote',
    'Par fokontany',
    'Par commune',
    'Par district',
    'Par region',
    'Par province',
    'Global',
  ];
  current = 0;
  election: Election | null = null;
  electoralResults: ElectoralResult[] = [];
  page: Page | null = null;

  pollingStations: PollingStation[] = [];
  regions: AdministrativeDivision[] = [];
  districts: AdministrativeDivision[] = [];
  communes: AdministrativeDivision[] = [];
  fokontany: AdministrativeDivision[] = [];

  regionId: string = '*';
  districtId: string = '*';
  communeId: string = '*';
  fokontanyId: string = '*';

  constructor(
    private route: ActivatedRoute,
    private electionService: ElectionService,
    private electionResultService: PresidentialElectionResultService,
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
            this.communeId,
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
    this.communeId = '*';
    this.fokontanyId = '*';
    this.filter(
      0,
      this.regionId,
      this.districtId,
      this.communeId,
      this.fokontanyId
    );
  }

  filter = (
    page: number = 0,
    regionId: string,
    districtId: string,
    communeId: string,
    fokontanyId: string
  ) => {
    this.regionId = regionId;
    this.districtId = districtId;
    this.communeId = communeId;
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
              this.communeId,
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
            .getFokontanyResults(
              page,
              1,
              electionId,
              this.regionId,
              this.districtId,
              this.communeId
            )
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults.content;
                this.page = payload.electoralResults.page;
              }
            });
          break;
        case 2:
          this.electionResultService
            .getCommunalResults(
              page,
              1,
              electionId,
              this.regionId,
              this.districtId
            )
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults.content;
                this.page = payload.electoralResults.page;
              }
            });
          break;
        case 3:
          this.electionResultService
            .getDistrictResults(page, 1, electionId, this.regionId)
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults.content;
                this.page = payload.electoralResults.page;
              }
            });
          break;
        case 4:
          this.electionResultService
            .getRegionalResults(electionId)
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults.content;
                this.page = payload.electoralResults.page;
              }
            });
          break;
        case 5:
          this.electionResultService
            .getProvincialResults(electionId)
            .then((payload) => {
              if (payload) {
                this.electoralResults = payload.electoralResults.content;
                this.page = payload.electoralResults.page;
              }
            });
          break;
        case 6:
          this.electionResultService
            .getGlobalResults(electionId)
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
          this.communeId,
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
          this.communeId,
          this.fokontanyId
        );
      }
    }
  };

  filterByRegion = (regionId: string) => {
    if (!regionId || regionId === '*') {
      this.districts = [];
      this.communes = [];
      this.fokontany = [];
      return;
    }
    this.administrativeDivisionService
      .getDistrictsByRegionId(parseInt(regionId))
      .then((payload) => {
        if (payload) {
          this.districts = payload.administrativeDivisions;
        }
      });
  };
  filterByDistrict = (districtId: string) => {
    if (!districtId || districtId === '*') {
      this.communes = [];
      this.fokontany = [];
      return;
    }
    this.administrativeDivisionService
      .getCommunesByDistrictId(parseInt(districtId))
      .then((payload) => {
        if (payload) {
          this.communes = payload.administrativeDivisions;
        }
      });
  };
  filterByCommune = (communeId: string) => {
    if (!communeId || communeId === '*') {
      this.fokontany = [];
      return;
    }
    this.administrativeDivisionService
      .getFokontanyByCommuneId(parseInt(communeId))
      .then((payload) => {
        if (payload) {
          this.fokontany = payload.administrativeDivisions;
        }
      });
  };

  async invalidateElectoralResult(electoralResult: ElectoralResult) {
    if (this.current == 0) {
      await this.electionResultService.invalidateElectoralResult(
        electoralResult.electionId,
        electoralResult.divisionId
      );
      this.filter(
        this.page?.number,
        this.regionId,
        this.districtId,
        this.communeId,
        this.fokontanyId
      );
    }
  }
}
