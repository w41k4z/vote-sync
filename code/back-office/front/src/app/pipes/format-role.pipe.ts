import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatRole',
})
export class FormatRolePipe implements PipeTransform {
  private roleTranslations: { [key: string]: string } = {
    ADMIN: 'Administrateur',
    MANAGER: 'Responsable',
    OPERATOR: 'Opérateur',
    CCID: 'Chef CID',
  };

  transform(role: string): string {
    return this.roleTranslations[role] || role;
  }
}
