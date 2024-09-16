import { Page } from './page';

export class PageModel<C> {
  content: C[];
  page: Page;

  constructor(content: C[], page: Page) {
    this.content = content;
    this.page = page;
  }
}
