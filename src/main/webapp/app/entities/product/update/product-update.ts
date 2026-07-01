import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { UnitOfMeasure } from 'app/entities/enumerations/unit-of-measure.model';
import { ISaleItem } from 'app/entities/sale-item/sale-item.model';
import { SaleItemService } from 'app/entities/sale-item/service/sale-item.service';
import { StockMovementService } from 'app/entities/stock-movement/service/stock-movement.service';
import { IStockMovement } from 'app/entities/stock-movement/stock-movement.model';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProduct, NewProduct } from '../product.model';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class ProductUpdate implements OnInit {
  readonly isSaving = signal(false);
  product: any = { id: null, active: false, deletedAt: null };
  unitOfMeasureValues = Object.keys(UnitOfMeasure);

  saleItemsSharedCollection = signal<ISaleItem[]>([]);
  stockMovementsSharedCollection = signal<IStockMovement[]>([]);

  protected productService = inject(ProductService);
  protected saleItemService = inject(SaleItemService);
  protected stockMovementService = inject(StockMovementService);
  protected activatedRoute = inject(ActivatedRoute);

  compareSaleItem = (o1: ISaleItem | null, o2: ISaleItem | null): boolean => this.saleItemService.compareSaleItem(o1, o2);

  compareStockMovement = (o1: IStockMovement | null, o2: IStockMovement | null): boolean =>
    this.stockMovementService.compareStockMovement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      if (product) {
        this.product = { ...product, deletedAt: product.deletedAt?.format(DATE_TIME_FORMAT) ?? null };
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const payload = { ...this.product, deletedAt: this.product.deletedAt ? dayjs(this.product.deletedAt, DATE_TIME_FORMAT) : null };
    if (this.product.id === null) {
      this.subscribeToSaveResponse(this.productService.create(payload as NewProduct));
    } else {
      this.subscribeToSaveResponse(this.productService.update(payload as IProduct));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IProduct | null>): void {
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
      .pipe(
        map((saleItems: ISaleItem[]) =>
          this.saleItemService.addSaleItemToCollectionIfMissing<ISaleItem>(saleItems, this.product?.saleItems),
        ),
      )
      .subscribe((saleItems: ISaleItem[]) => this.saleItemsSharedCollection.set(saleItems));

    this.stockMovementService
      .query()
      .pipe(map((res: HttpResponse<IStockMovement[]>) => res.body ?? []))
      .pipe(
        map((stockMovements: IStockMovement[]) =>
          this.stockMovementService.addStockMovementToCollectionIfMissing<IStockMovement>(stockMovements, this.product?.stockMovements),
        ),
      )
      .subscribe((stockMovements: IStockMovement[]) => this.stockMovementsSharedCollection.set(stockMovements));
  }
}
