import { AfterViewInit, Component, ElementRef, inject, signal, viewChild, ChangeDetectionStrategy } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { TranslateModule } from '@ngx-translate/core';

import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';

import { PasswordResetInitService } from './password-reset-init.service';

@Component({
  selector: 'jhi-password-reset-init',
  imports: [TranslateDirective, TranslateModule, AlertError, FormsModule],
  changeDetection: ChangeDetectionStrategy.Eager,
  templateUrl: './password-reset-init.html',
})
export default class PasswordResetInit implements AfterViewInit {
  email = viewChild.required<ElementRef>('email');

  readonly success = signal(false);
  resetRequestForm = { email: '' };

  private readonly passwordResetInitService = inject(PasswordResetInitService);

  ngAfterViewInit(): void {
    this.email().nativeElement.focus();
  }

  requestReset(): void {
    this.passwordResetInitService.save(this.resetRequestForm.email).subscribe({
      next: () => this.success.set(true),
      error() {
        // Ignore error
      },
    });
  }
}
