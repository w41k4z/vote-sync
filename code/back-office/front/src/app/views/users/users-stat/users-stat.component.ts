import { Component, Input } from '@angular/core';
import { UserStat } from '../../../dto/user-stat';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-users-stat',
  templateUrl: './users-stat.component.html',
  styleUrl: './users-stat.component.scss',
})
export class UsersStatComponent {
  @Input() loading$!: Observable<Boolean>;
  @Input() stats!: UserStat[];
}
