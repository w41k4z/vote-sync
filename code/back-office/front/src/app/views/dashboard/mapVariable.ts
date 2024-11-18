import { latLng, tileLayer } from 'leaflet';

export const layers = [
  tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 18,
    attribution: '...',
  }),
];

export const madagascarLatLong = latLng(-18.8792, 47.5079);
