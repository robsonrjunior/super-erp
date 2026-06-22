import { Component, Renderer2, inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
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

  readonly themeOptions = [
    { icon: 'pi pi-sun', value: false, label: 'global.footer.lightMode' },
    { icon: 'pi pi-moon', value: true, label: 'global.footer.darkMode' },
  ];

  selectedLanguage = 'pt-br';
  selectedTheme = false;

  private readonly translateService = inject(TranslateService);
  private readonly stateStorageService = inject(StateStorageService);
  private readonly document = inject(DOCUMENT);
  private readonly renderer = inject(Renderer2);

  constructor() {
    this.selectedLanguage = this.translateService.getCurrentLang() || 'pt-br';
    this.selectedTheme = localStorage.getItem('dark-mode') === 'true';
    this.applyDarkMode(this.selectedTheme);
  }

  changeLanguage(languageKey: string): void {
    this.selectedLanguage = languageKey;

    this.stateStorageService.storeLocale(languageKey);
    this.translateService.use(languageKey);
  }

  setDarkMode(dark: boolean): void {
    this.selectedTheme = dark;
    localStorage.setItem('dark-mode', String(dark));
    this.applyDarkMode(dark);
  }

  private applyDarkMode(dark: boolean): void {
    const html = this.document.documentElement;
    if (dark) {
      this.renderer.addClass(html, 'dark-mode');
      this.renderer.setAttribute(html, 'data-bs-theme', 'dark');
    } else {
      this.renderer.removeClass(html, 'dark-mode');
      this.renderer.removeAttribute(html, 'data-bs-theme');
    }
  }
}
