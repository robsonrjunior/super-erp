import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewInit, Component, ElementRef, inject, signal, viewChild, ChangeDetectionStrategy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { TranslateModule, TranslateService } from '@ngx-translate/core';

import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared/jhipster/error.constants';
import { TranslateDirective } from 'app/shared/language';
import PasswordStrengthBar from '../password/password-strength-bar/password-strength-bar';

import { RegisterService } from './register.service';

@Component({
  selector: 'jhi-register',
  imports: [TranslateDirective, TranslateModule, RouterLink, FormsModule, PasswordStrengthBar],
  changeDetection: ChangeDetectionStrategy.Eager,
  templateUrl: './register.html',
})
export default class Register implements AfterViewInit {
  login = viewChild.required<ElementRef>('login');

  readonly doNotMatch = signal(false);
  readonly error = signal(false);
  readonly errorEmailExists = signal(false);
  readonly errorUserExists = signal(false);
  readonly success = signal(false);

  registerForm = { login: '', email: '', password: '', confirmPassword: '' };

  private readonly translateService = inject(TranslateService);
  private readonly registerService = inject(RegisterService);

  ngAfterViewInit(): void {
    this.login().nativeElement.focus();
  }

  register(): void {
    this.doNotMatch.set(false);
    this.error.set(false);
    this.errorEmailExists.set(false);
    this.errorUserExists.set(false);

    const { password, confirmPassword } = this.registerForm;
    if (password === confirmPassword) {
      const { login, email } = this.registerForm;
      this.registerService
        .save({ login, email, password, langKey: this.translateService.getCurrentLang() })
        .subscribe({ next: () => this.success.set(true), error: response => this.processError(response) });
    } else {
      this.doNotMatch.set(true);
    }
  }

  private processError(response: HttpErrorResponse): void {
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists.set(true);
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists.set(true);
    } else {
      this.error.set(true);
    }
  }
}
