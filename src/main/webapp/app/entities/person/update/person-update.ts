import { Component, OnInit, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbInputDatepicker } from '@ng-bootstrap/ng-bootstrap/datepicker';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IPerson, NewPerson } from '../person.model';
import { PersonService } from '../service/person.service';

@Component({
  selector: 'jhi-person-update',
  templateUrl: './person-update.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule, NgbInputDatepicker],
})
export class PersonUpdate implements OnInit {
  readonly isSaving = signal(false);
  person: any = { id: null, active: false, deletedAt: null };

  protected personService = inject(PersonService);
  protected activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ person }) => {
      if (person) {
        this.person = { ...person, deletedAt: person.deletedAt?.format(DATE_TIME_FORMAT) ?? null };
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const payload = { ...this.person, deletedAt: this.person.deletedAt ? dayjs(this.person.deletedAt, DATE_TIME_FORMAT) : null };
    if (this.person.id === null) {
      this.subscribeToSaveResponse(this.personService.create(payload as NewPerson));
    } else {
      this.subscribeToSaveResponse(this.personService.update(payload as IPerson));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IPerson | null>): void {
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
