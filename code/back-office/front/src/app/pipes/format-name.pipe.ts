import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatName',
})
export class FormatNamePipe implements PipeTransform {
  transform(name: string): unknown {
    const romanNumerals = ['I', 'II', 'III', 'IV', 'V', 'VI'];
    // Convert the string to lowercase, then split it by space to work on each word
    return name
      .toLowerCase()
      .split(' ')
      .map((word, _) => {
        if (romanNumerals.includes(word.toUpperCase())) {
          return word.toUpperCase();
        }

        return word.charAt(0).toUpperCase() + word.slice(1);
      })
      .join(' ');
  }
}
