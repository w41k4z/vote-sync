import { Component } from '@angular/core';
import { LayerGroup, geoJSON } from 'leaflet';
import { ElectionStatService } from '../../services/api/election/stat/election-stat.service';
import { VotersStat } from '../../dto/response/voters-stat';
import { administrativeDivisions } from './administrativeDivision';
import { layers, madagascarLatLong } from './mapVariable';
import { getPopupHtml } from './popupHtml';
import { AdministrativeDivisionStats } from '../../dto/administrative-division-stats';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from '../../components/delete-dialog/delete-dialog.component';
import { StatDetailsComponent } from './stat-details/stat-details.component';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  geojsonLayers: LayerGroup[] = [];
  votersStats: VotersStat[] = [];
  divisions = administrativeDivisions;
  currentDivisionId = 0;
  currentZoom = this.divisions[this.currentDivisionId].value.zoom;
  electionTypes = [
    {
      name: 'Toutes',
      value: -1,
    },
    {
      name: 'Présidentielle',
      value: 1,
    },
    {
      name: 'Législative',
      value: 2,
    },
    {
      name: 'Locale',
      value: 3,
    },
  ];
  electionTypeId = this.electionTypes[0].value;

  mapLayers = layers;
  options = {
    layers: this.mapLayers,
    zoom: this.currentZoom,
    center: madagascarLatLong,
  };

  constructor(
    private dialog: MatDialog,
    private electionStatService: ElectionStatService
  ) {
    this.electionStatService.getVotersStats().then((payload) => {
      if (payload) {
        this.votersStats = payload.votersStats;
      }
    });
    this.getAdministrativeDivisionStats();
  }

  getAdministrativeDivisionStats() {
    this.geojsonLayers = [];
    this.electionStatService
      .getAdministrativeDivisionStats(
        this.divisions[this.currentDivisionId].value.name,
        this.electionTypeId == -1 ? undefined : this.electionTypeId
      )
      .then((payload) => {
        if (payload) {
          for (let stat of payload.stats) {
            const geojson = JSON.parse(stat.geojson);
            const callBack = () => {
              this.openDetailsDialog(stat);
            };
            this.geojsonLayers.push(
              geoJSON(geojson, {
                onEachFeature(feature, layer) {
                  let popupHtml = getPopupHtml(stat);
                  layer.bindPopup(popupHtml);
                  layer.on('popupopen', () => {
                    const button = document.getElementById('details-button');
                    if (button) {
                      button.addEventListener('click', () => {
                        callBack();
                      });
                    }
                  });
                },
              })
            );
          }
        }
      });
  }

  openDetailsDialog = (division: AdministrativeDivisionStats) => {
    const data =
      this.electionTypeId == -1
        ? {
            division: this.divisions[this.currentDivisionId].value.name,
            divisionId: division.divisionId,
            divisionName: division.divisionName,
          }
        : {
            division: this.divisions[this.currentDivisionId].value.name,
            divisionId: division.divisionId,
            divisionName: division.divisionName,
            electionTypeId: this.electionTypeId,
          };
    this.dialog.open(StatDetailsComponent, {
      data: data,
      width: 'auto',
      maxWidth: 'none',
    });
  };
}
