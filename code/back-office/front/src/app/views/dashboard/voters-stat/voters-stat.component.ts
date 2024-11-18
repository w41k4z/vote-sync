import { Component, Input } from '@angular/core';
import { ChartConfiguration, ChartOptions } from 'chart.js';
import { VotersStat } from '../../../dto/response/voters-stat';

@Component({
  selector: 'app-voters-stat',
  templateUrl: './voters-stat.component.html',
  styleUrl: './voters-stat.component.scss',
})
export class VotersStatComponent {
  @Input() votersStats!: VotersStat[];

  ngOnChanges(): void {
    if (this.votersStats) {
      this.lineChartData = {
        labels: this.votersStats.map((votesrStat) => votesrStat.election),
        datasets: [
          {
            data: this.votersStats.map((votesrStat) => votesrStat.voters),
            label: 'Inscrits',
            fill: true,
            tension: 0.5,
            borderColor: 'black',
            backgroundColor: 'rgba(255,0,0,0.3)',
          },
          {
            data: this.votersStats.map((votesrStat) => votesrStat.registered),
            label: 'Enregistrés',
            fill: true,
            tension: 0.5,
            borderColor: 'black',
            backgroundColor: 'rgba(255,255,0,0.3)',
          },
        ],
      };
    }
  }

  public lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [],
  };

  public lineChartOptions: ChartOptions<'line'> = {
    responsive: true,
  };
}
