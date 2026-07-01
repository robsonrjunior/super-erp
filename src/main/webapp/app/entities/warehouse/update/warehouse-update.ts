import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISale } from 'app/entities/sale/sale.model';
import { SaleService } from 'app/entities/sale/service/sale.service';
import { StockMovementService } from 'app/entities/stock-movement/service/stock-movement.service';
import { IStockMovement } from 'app/entities/stock-movement/stock-movement.model';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { WarehouseService } from '../service/warehouse.service';
import { IWarehouse, NewWarehouse } from '../warehouse.model';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

@Component({
  selector: 'jhi-warehouse-update',
  templateUrl: './warehouse-update.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class WarehouseUpdate implements OnInit {
  readonly isSaving = signal(false);
  warehouse: any = { id: null, active: false, deletedAt: null };

  stockMovementsSharedCollection = signal<IStockMovement[]>([]);
  salesSharedCollection = signal<ISale[]>([]);

  protected warehouseService = inject(WarehouseService);
  protected stockMovementService = inject(StockMovementService);
  protected saleService = inject(SaleService);
  protected activatedRoute = inject(ActivatedRoute);

  compareStockMovement = (o1: IStockMovement | null, o2: IStockMovement | null): boolean =>
    this.stockMovementService.compareStockMovement(o1, o2);

  compareSale = (o1: ISale | null, o2: ISale | null): boolean => this.saleService.compareSale(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ warehouse }) => {
      if (warehouse) {
        this.warehouse = { ...warehouse, deletedAt: warehouse.deletedAt?.format(DATE_TIME_FORMAT) ?? null };
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const payload = {
      ...this.warehouse,
      deletedAt: this.warehouse.deletedAt ? dayjs(this.warehouse.deletedAt, DATE_TIME_FORMAT) : null,
    };
    if (this.warehouse.id === null) {
      this.subscribeToSaveResponse(this.warehouseService.create(payload as NewWarehouse));
    } else {
      this.subscribeToSaveResponse(this.warehouseService.update(payload as IWarehouse));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IWarehouse | null>): void {
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
    this.stockMovementService
      .query()
      .pipe(map((res: HttpResponse<IStockMovement[]>) => res.body ?? []))
      .pipe(
        map((stockMovements: IStockMovement[]) =>
          this.stockMovementService.addStockMovementToCollectionIfMissing<IStockMovement>(stockMovements, this.warehouse?.stockMovements),
        ),
      )
      .subscribe((stockMovements: IStockMovement[]) => this.stockMovementsSharedCollection.set(stockMovements));

    this.saleService
      .query()
      .pipe(map((res: HttpResponse<ISale[]>) => res.body ?? []))
      .pipe(map((sales: ISale[]) => this.saleService.addSaleToCollectionIfMissing<ISale>(sales, this.warehouse?.sales)))
      .subscribe((sales: ISale[]) => this.salesSharedCollection.set(sales));
  }
}
