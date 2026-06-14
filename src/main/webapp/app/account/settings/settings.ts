import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { TranslateModule, TranslateService } from '@ngx-translate/core';

import { LANGUAGES } from 'app/config/language.constants';
import { AccountService } from 'app/core/auth/account.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { FindLanguageFromKeyPipe, TranslateDirective } from 'app/shared/language';

@Component({
  selector: 'jhi-settings',
  imports: [TranslateDirective, TranslateModule, FindLanguageFromKeyPipe, AlertError, FormsModule],
  templateUrl: './settings.html',
})
export default class Settings implements OnInit {
  readonly success = signal(false);
  languages = LANGUAGES;

  settingsForm: any = {
    firstName: '',
    lastName: '',
    email: '',
    langKey: '',
    activated: false,
    authorities: [] as string[],
    imageUrl: '',
    login: '',
  };

  private readonly accountService = inject(AccountService);
  private readonly translateService = inject(TranslateService);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm = { ...account };
      }
    });
  }

  save(): void {
    this.success.set(false);

    const account = this.settingsForm;
    this.accountService.save(account).subscribe({
      next: () => {
        this.success.set(true);

        this.accountService.authenticate(account);

        if (account.langKey !== this.translateService.getCurrentLang()) {
          this.translateService.use(account.langKey);
        }
      },
      error() {
        // Handled by interceptor.
      },
    });
  }
}
