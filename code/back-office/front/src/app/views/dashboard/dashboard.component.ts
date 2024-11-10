import { Component } from '@angular/core';
import {
  LayerGroup,
  circle,
  latLng,
  polygon,
  tileLayer,
  geoJSON,
} from 'leaflet';
import { AdministrativeDivisionService } from '../../services/api/administrative-division/administrative-division.service';
import { ChartConfiguration, ChartOptions, ChartType } from 'chart.js';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  geojsonLayers: LayerGroup[] = [];

  constructor(private service: AdministrativeDivisionService) {
    this.service.getRegionsStat().then((payload) => {
      console.log(payload);
      if (payload) {
        for (let region of payload.administrativeDivisions) {
          const geojson = JSON.parse(region.geojson);
          this.geojsonLayers.push(
            geoJSON(geojson, {
              onEachFeature(feature, layer) {
                let popupHtml = '<div class="custom-popup">';
                popupHtml += '<table>';
                popupHtml += '<thead>';
                popupHtml += '<tr>';
                popupHtml += '<th scope="col">' + region.name + '</th>';
                popupHtml += '<th scope="col"></th>';
                popupHtml += '</tr>';
                popupHtml += '</thead>';
                popupHtml += '<tbody>';
                popupHtml += '<tr>';
                popupHtml += '<td>Abstention</td>';
                popupHtml += '<td class="text-end text-danger">10%</td>';
                popupHtml += '</tr>';
                popupHtml += '<tr>';
                popupHtml += '<td>Couverture</td>';
                popupHtml += '<td class="text-end">40%</td>';
                popupHtml += '</tr>';
                popupHtml += '<tr>';
                popupHtml += '<td>Alertes</td>';
                popupHtml += '<td class="text-end">2</td>';
                popupHtml += '</tr>';
                popupHtml += '</tbody>';
                popupHtml += '</table>';
                popupHtml += '</div>';
                layer.bindPopup(popupHtml);
              },
            })
          );
        }
      }
    });
  }

  options = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 18,
        attribution: '...',
      }),
    ],
    zoom: 5,
    center: latLng(-20.2, 47.5),
  };

  layersControl = {
    baseLayers: {
      'Open Street Map': tileLayer(
        'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
        { maxZoom: 18, attribution: '...' }
      ),
      'Open Cycle Map': tileLayer(
        'https://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png',
        { maxZoom: 18, attribution: '...' }
      ),
    },
    overlays: {
      'Big Circle': circle([46.95, -122], { radius: 5000 }),
      'Big Square': polygon([
        [46.8, -121.55],
        [46.9, -121.55],
        [46.9, -121.7],
        [46.8, -121.7],
      ]),
    },
  };

  public lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: ['Presidentielle 2023', 'Legisltative 2024', 'Municipale 2024'],
    datasets: [
      {
        data: [35, 20, 40],
        label: 'Inscrits',
        fill: true,
        tension: 0.5,
        borderColor: 'black',
        backgroundColor: 'rgba(255,0,0,0.3)',
      },
      {
        data: [30, 19, 32],
        label: 'Enregistrés',
        fill: true,
        tension: 0.5,
        borderColor: 'black',
        backgroundColor: 'rgba(255,255,0,0.3)',
      },
    ],
  };
  public lineChartOptions: ChartOptions<'line'> = {
    responsive: true,
  };

  public barChartData: ChartConfiguration<'bar'>['data'] = {
    labels: ['Presidentielle 2023', 'Legisltative 2024', 'Municipale 2024'],
    datasets: [
      { data: [20, 10, 25], label: 'Homme' },
      { data: [10, 9, 15], label: 'Femme' },
    ],
  };
  public barChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: false,
  };
}
