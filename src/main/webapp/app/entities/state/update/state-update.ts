import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICountry } from 'app/entities/country/country.model';
import { CountryService } from 'app/entities/country/service/country.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { StateService } from '../service/state.service';
import { IState, NewState } from '../state.model';

@Component({
  selector: 'jhi-state-update',
  templateUrl: './state-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class StateUpdate implements OnInit {
  readonly isSaving = signal(false);
  state: IState | NewState = { id: null };

  countriesSharedCollection = signal<ICountry[]>([]);

  protected stateService = inject(StateService);
  protected countryService = inject(CountryService);
  protected activatedRoute = inject(ActivatedRoute);

  compareCountry = (o1: ICountry | null, o2: ICountry | null): boolean => this.countryService.compareCountry(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ state }) => {
      if (state) {
        this.state = state;
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    if (this.state.id === null) {
      this.subscribeToSaveResponse(this.stateService.create(this.state));
    } else {
      this.subscribeToSaveResponse(this.stateService.update(this.state));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IState | null>): void {
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
    this.countryService
      .query()
      .pipe(map((res: HttpResponse<ICountry[]>) => res.body ?? []))
      .pipe(map((countries: ICountry[]) => this.countryService.addCountryToCollectionIfMissing<ICountry>(countries, this.state.country)))
      .subscribe((countries: ICountry[]) => this.countriesSharedCollection.set(countries));
  }
}
