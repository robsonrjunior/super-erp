import { Component, OnInit, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import dayjs from 'dayjs/esm';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { MovementType } from 'app/entities/enumerations/movement-type.model';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { StockMovementService } from '../service/stock-movement.service';
import { IStockMovement, NewStockMovement } from '../stock-movement.model';

@Component({
  selector: 'jhi-stock-movement-update',
  templateUrl: './stock-movement-update.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class StockMovementUpdate implements OnInit {
  readonly isSaving = signal(false);
  stockMovement: any = { id: null, movementDate: null, deletedAt: null };
  movementTypeValues = Object.keys(MovementType);

  protected stockMovementService = inject(StockMovementService);
  protected activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockMovement }) => {
      if (stockMovement) {
        this.stockMovement = {
          ...stockMovement,
          deletedAt: stockMovement.deletedAt?.format(DATE_TIME_FORMAT) ?? null,
          movementDate: stockMovement.movementDate?.format(DATE_TIME_FORMAT) ?? null,
        };
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const payload = {
      ...this.stockMovement,
      deletedAt: this.stockMovement.deletedAt ? dayjs(this.stockMovement.deletedAt, DATE_TIME_FORMAT) : null,
      movementDate: this.stockMovement.movementDate ? dayjs(this.stockMovement.movementDate, DATE_TIME_FORMAT) : null,
    };
    if (this.stockMovement.id === null) {
      this.subscribeToSaveResponse(this.stockMovementService.create(payload as NewStockMovement));
    } else {
      this.subscribeToSaveResponse(this.stockMovementService.update(payload as IStockMovement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IStockMovement | null>): void {
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
