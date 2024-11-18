import { Component, Input } from '@angular/core';
import { ChartConfiguration } from 'chart.js';
import { VotersStat } from '../../../dto/response/voters-stat';

@Component({
  selector: 'app-voters-repartition-stat',
  templateUrl: './voters-repartition-stat.component.html',
  styleUrl: './voters-repartition-stat.component.scss',
})
export class VotersRepartitionStatComponent {
  @Input() votersStats!: VotersStat[];

  ngOnChanges(): void {
    if (this.votersStats) {
      this.barChartData = {
        labels: this.votersStats.map((votesrStat) => votesrStat.election),
        datasets: [
          {
            data: this.votersStats.map((votesrStat) => votesrStat.maleUnder36),
            label: 'Homme moins de 36 ans',
          },
          {
            data: this.votersStats.map(
              (votesrStat) => votesrStat.femaleUnder36
            ),
            label: 'Femme moins de 36 ans',
          },
          {
            data: this.votersStats.map(
              (votesrStat) => votesrStat.male36AndOver
            ),
            label: 'Homme 36 ans et plus',
          },
          {
            data: this.votersStats.map(
              (votesrStat) => votesrStat.female36AndOver
            ),
            label: 'Femme 36 ans et plus',
          },
          {
            data: this.votersStats.map(
              (votesrStat) => votesrStat.disabledPeople
            ),
            label: 'Handicapés',
          },
          {
            data: this.votersStats.map(
              (votesrStat) => votesrStat.visuallyImpairedPeople
            ),
            label: 'Malvoyants',
          },
        ],
      };
    }
  }

  public barChartData: ChartConfiguration<'bar'>['data'] = {
    labels: [],
    datasets: [],
  };
  public barChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: false,
  };
}
