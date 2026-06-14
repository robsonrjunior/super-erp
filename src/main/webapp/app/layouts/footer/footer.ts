import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { TranslateModule, TranslateService } from '@ngx-translate/core';

import { StateStorageService } from 'app/core/auth/state-storage.service';

import { SelectButtonModule } from 'primeng/selectbutton';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.html',
  styleUrl: './footer.scss',
  imports: [FormsModule, TranslateModule, SelectButtonModule],
})
export default class Footer {
  currentYear = new Date().getFullYear();

  readonly languageOptions = [
    {
      label: 'Português',
      value: 'pt-br',
      flag: 'content/images/flags/4x3/br.svg',
    },
    {
      label: 'English',
      value: 'en',
      flag: 'content/images/flags/4x3/us.svg',
    },
    {
      label: 'Español',
      value: 'es',
      flag: 'content/images/flags/4x3/es.svg',
    },
  ];

  selectedLanguage = 'pt-br';

  private readonly translateService = inject(TranslateService);
  private readonly stateStorageService = inject(StateStorageService);

  constructor() {
    this.selectedLanguage = this.translateService.getCurrentLang() || 'pt-br';
  }

  changeLanguage(languageKey: string): void {
    this.selectedLanguage = languageKey;

    this.stateStorageService.storeLocale(languageKey);
    this.translateService.use(languageKey);
  }
}
