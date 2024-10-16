import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatNumber',
})
export class FormatNumberPipe implements PipeTransform {
  transform(value: number): unknown {
    if (value) {
      let formatted = value
        .toFixed(2)
        .toString()
        .replace(/\B(?=(\d{3})+(?!\d))/g, ' ');
      if (formatted.endsWith('.00')) {
        formatted = formatted.slice(0, -3);
      }
      return formatted;
    }
    return value;
  }
}
