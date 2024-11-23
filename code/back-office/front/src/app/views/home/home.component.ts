import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  sections = [
    {
      title: 'Suivi des tendances',
      description:
        'Analysez les données électorales grâce au tableau de bord interactif.',
      icon: 'bi-bar-chart',
    },
    {
      title: 'Information des agents électoraux',
      description:
        'Consultez et gérez les données des agents participant aux élections.',
      icon: 'bi-people',
    },
    {
      title: 'Résultats en temps réel',
      description:
        'Accédez aux résultats des élections en cours en temps réel.',
      icon: 'bi-speedometer2',
    },
    {
      title: 'Historiques d’élections',
      description:
        'Découvrez les résultats et les informations des élections précédentes.',
      icon: 'bi-archive',
    },
  ];
}
