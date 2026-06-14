import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICompany, NewCompany } from '../company.model';
import { CompanyService } from '../service/company.service';

@Component({
  selector: 'jhi-company-update',
  templateUrl: './company-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class CompanyUpdate implements OnInit {
  readonly isSaving = signal(false);
  company: any = { id: null, active: false, deletedAt: null };

  protected companyService = inject(CompanyService);
  protected activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ company }) => {
      if (company) {
        this.company = { ...company, deletedAt: company.deletedAt?.format(DATE_TIME_FORMAT) ?? null };
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const payload = { ...this.company, deletedAt: this.company.deletedAt ? dayjs(this.company.deletedAt, DATE_TIME_FORMAT) : null };
    if (this.company.id === null) {
      this.subscribeToSaveResponse(this.companyService.create(payload as NewCompany));
    } else {
      this.subscribeToSaveResponse(this.companyService.update(payload as ICompany));
    }
  }

  protected subscribeToSaveResponse(result: Observable<ICompany | null>): void {
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
