import { Component, OnInit, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { ISaleItem, NewSaleItem } from '../sale-item.model';
import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { SaleItemService } from '../service/sale-item.service';

@Component({
  selector: 'jhi-sale-item-update',
  templateUrl: './sale-item-update.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class SaleItemUpdate implements OnInit {
  readonly isSaving = signal(false);
  saleItem: any = { id: null, deletedAt: null };

  protected saleItemService = inject(SaleItemService);
  protected activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ saleItem }) => {
      if (saleItem) {
        this.saleItem = { ...saleItem, deletedAt: saleItem.deletedAt?.format(DATE_TIME_FORMAT) ?? null };
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const payload = {
      ...this.saleItem,
      deletedAt: this.saleItem.deletedAt ? dayjs(this.saleItem.deletedAt, DATE_TIME_FORMAT) : null,
    };
    if (this.saleItem.id === null) {
      this.subscribeToSaveResponse(this.saleItemService.create(payload as NewSaleItem));
    } else {
      this.subscribeToSaveResponse(this.saleItemService.update(payload as ISaleItem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<ISaleItem | null>): void {
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
