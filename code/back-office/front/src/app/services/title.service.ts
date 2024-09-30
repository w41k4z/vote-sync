import { Injectable } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root',
})
export class TitleService {
  constructor(private title: Title) {}

  getTitle(): string {
    return this.title.getTitle();
  }

  setTitle(title: string): void {
    this.title.setTitle(title);
  }
}
