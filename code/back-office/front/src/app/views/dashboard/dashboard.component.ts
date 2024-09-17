import { Component } from '@angular/core';
import {
  LayerGroup,
  circle,
  latLng,
  polygon,
  tileLayer,
  geoJSON,
} from 'leaflet';
import { StatService } from '../../services/api/stat/stat.service';
import { RegionStatPayload } from '../../dto/response/stat/region-stat-payload.response';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  geojsonLayers: LayerGroup[] = [];

  constructor(private statService: StatService) {
    this.statService.getRegionsStat().then((payload) => {
      if (payload) {
        for (let region of payload.regions) {
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
                popupHtml += '<td>Districts</td>';
                popupHtml += '<td class="text-end">10</td>';
                popupHtml += '</tr>';
                popupHtml += '<tr>';
                popupHtml += '<td>Communes</td>';
                popupHtml += '<td class="text-end">1000</td>';
                popupHtml += '</tr>';
                popupHtml += '<tr>';
                popupHtml += '<td>Fokontany</td>';
                popupHtml += '<td class="text-end">10000</td>';
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
}
