import { Component } from '@angular/core';
import { ApiCallService } from '../../services/api/api-call.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent extends ApiCallService {
  constructor(httpClient: HttpClient) {
    super(httpClient);
    this.httpClient.get('http://localhost:8081/api/users').subscribe();
  }
}
