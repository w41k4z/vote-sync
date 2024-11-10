import { Component } from '@angular/core';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrl: './alert.component.scss',
})
export class AlertComponent {
  selectedRegion: string = 'tous';
  selectedDistrict: string = 'tous';
  selectedCommune: string = 'tous';
  selectedFokontany: string = 'tous';
  selectedAlertLevel: string = 'tous';

  alerts = [
    {
      pollingStation: 'Epp Anosipatrana Salle 2',
      type: 'Enregistrement suspect',
      alertType: 'suspect',
      description:
        "Enregistrement de 4 électeurs en dehors des heures d'ouverture des bureaux de vote.",
      state: 'en-attente',
      bgState: 'bg-warning',
    },
    {
      pollingStation: 'Epp Ilanivato Salle 1',
      type: 'Non synchronisé',
      alertType: 'information',
      description:
        "Les résultats sont présentes, mais pas d'enregistrement d'électeur.",
      state: 'resolue',
      bgState: 'bg-success',
    },
  ];
}
