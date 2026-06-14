import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { ICountry, NewCountry } from '../country.model';
import { CountryService } from '../service/country.service';

@Component({
  selector: 'jhi-country-update',
  templateUrl: './country-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class CountryUpdate implements OnInit {
  readonly isSaving = signal(false);
  country: ICountry | NewCountry = { id: null };

  protected countryService = inject(CountryService);
  protected activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ country }) => {
      if (country) {
        this.country = country;
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    if (this.country.id === null) {
      this.subscribeToSaveResponse(this.countryService.create(this.country));
    } else {
      this.subscribeToSaveResponse(this.countryService.update(this.country));
    }
  }

  protected subscribeToSaveResponse(result: Observable<ICountry | null>): void {
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
