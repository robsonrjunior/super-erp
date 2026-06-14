import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SaleStatus } from 'app/entities/enumerations/sale-status.model';
import { ISaleItem } from 'app/entities/sale-item/sale-item.model';
import { SaleItemService } from 'app/entities/sale-item/service/sale-item.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { ISale, NewSale } from '../sale.model';
import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { SaleService } from '../service/sale.service';

@Component({
  selector: 'jhi-sale-update',
  templateUrl: './sale-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class SaleUpdate implements OnInit {
  readonly isSaving = signal(false);
  sale: any = { id: null, deletedAt: null };
  saleStatusValues = Object.keys(SaleStatus);

  saleItemsSharedCollection = signal<ISaleItem[]>([]);

  protected saleService = inject(SaleService);
  protected saleItemService = inject(SaleItemService);
  protected activatedRoute = inject(ActivatedRoute);

  compareSaleItem = (o1: ISaleItem | null, o2: ISaleItem | null): boolean => this.saleItemService.compareSaleItem(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sale }) => {
      if (sale) {
        this.sale = { ...sale, deletedAt: sale.deletedAt?.format(DATE_TIME_FORMAT) ?? null };
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const payload = { ...this.sale, deletedAt: this.sale.deletedAt ? dayjs(this.sale.deletedAt, DATE_TIME_FORMAT) : null };
    if (this.sale.id === null) {
      this.subscribeToSaveResponse(this.saleService.create(payload as NewSale));
    } else {
      this.subscribeToSaveResponse(this.saleService.update(payload as ISale));
    }
  }

  protected subscribeToSaveResponse(result: Observable<ISale | null>): void {
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

  protected loadRelationshipsOptions(): void {
    this.saleItemService
      .query()
      .pipe(map((res: HttpResponse<ISaleItem[]>) => res.body ?? []))
      .pipe(map((saleItems: ISaleItem[]) => this.saleItemService.addSaleItemToCollectionIfMissing<ISaleItem>(saleItems, this.sale?.items)))
      .subscribe((saleItems: ISaleItem[]) => this.saleItemsSharedCollection.set(saleItems));
  }
}
