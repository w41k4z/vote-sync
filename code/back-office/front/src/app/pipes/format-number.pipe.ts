import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatNumber',
})
export class FormatNumberPipe implements PipeTransform {
  transform(value: Number): unknown {
    if (value) {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ' ');
    }
    return value;
  }
}
