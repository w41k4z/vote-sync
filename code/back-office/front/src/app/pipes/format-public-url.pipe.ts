import { Pipe, PipeTransform } from '@angular/core';
import { env } from '../../environment/env';

@Pipe({
  name: 'formatPublicUrl',
})
export class FormatPublicUrlPipe implements PipeTransform {
  transform(value: string): unknown {
    return `${env.baseUrl}/public/${value}`;
  }
}
