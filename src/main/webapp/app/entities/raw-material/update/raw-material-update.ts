import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { UnitOfMeasure } from 'app/entities/enumerations/unit-of-measure.model';
import { StockMovementService } from 'app/entities/stock-movement/service/stock-movement.service';
import { IStockMovement } from 'app/entities/stock-movement/stock-movement.model';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IRawMaterial, NewRawMaterial } from '../raw-material.model';
import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { RawMaterialService } from '../service/raw-material.service';

@Component({
  selector: 'jhi-raw-material-update',
  templateUrl: './raw-material-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class RawMaterialUpdate implements OnInit {
  readonly isSaving = signal(false);
  rawMaterial: any = { id: null, active: false, deletedAt: null };
  unitOfMeasureValues = Object.keys(UnitOfMeasure);

  stockMovementsSharedCollection = signal<IStockMovement[]>([]);

  protected rawMaterialService = inject(RawMaterialService);
  protected stockMovementService = inject(StockMovementService);
  protected activatedRoute = inject(ActivatedRoute);

  compareStockMovement = (o1: IStockMovement | null, o2: IStockMovement | null): boolean =>
    this.stockMovementService.compareStockMovement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rawMaterial }) => {
      if (rawMaterial) {
        this.rawMaterial = { ...rawMaterial, deletedAt: rawMaterial.deletedAt?.format(DATE_TIME_FORMAT) ?? null };
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
      ...this.rawMaterial,
      deletedAt: this.rawMaterial.deletedAt ? dayjs(this.rawMaterial.deletedAt, DATE_TIME_FORMAT) : null,
    };
    if (this.rawMaterial.id === null) {
      this.subscribeToSaveResponse(this.rawMaterialService.create(payload as NewRawMaterial));
    } else {
      this.subscribeToSaveResponse(this.rawMaterialService.update(payload as IRawMaterial));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IRawMaterial | null>): void {
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
          this.stockMovementService.addStockMovementToCollectionIfMissing<IStockMovement>(stockMovements, this.rawMaterial?.stockMovements),
        ),
      )
      .subscribe((stockMovements: IStockMovement[]) => this.stockMovementsSharedCollection.set(stockMovements));
  }
}
