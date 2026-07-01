import { Component, OnInit, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IAuthority, NewAuthority } from '../authority.model';
import { AuthorityService } from '../service/authority.service';

@Component({
  selector: 'jhi-authority-update',
  templateUrl: './authority-update.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class AuthorityUpdate implements OnInit {
  readonly isSaving = signal(false);
  authority: any = { id: null };

  protected authorityService = inject(AuthorityService);
  protected activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ authority }) => {
      if (authority) {
        this.authority = authority;
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    this.subscribeToSaveResponse(this.authorityService.create(this.authority as NewAuthority));
  }

  protected subscribeToSaveResponse(result: Observable<IAuthority | null>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving.set(false);
  }
}
