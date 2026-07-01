import { Component, OnInit, computed, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { LANGUAGES } from 'app/config/language.constants';
import { AlertError } from 'app/shared/alert/alert-error';
import { FindLanguageFromKeyPipe, TranslateDirective } from 'app/shared/language';
import { AuthorityService } from '../../authority/service/authority.service';
import { UserManagementService } from '../service/user-management.service';
import { IUserManagement, NewUserManagement } from '../user-management.model';

const userTemplate = {} as IUserManagement;

const newUser: IUserManagement = {
  langKey: 'pt-br',
  activated: true,
} as IUserManagement;

@Component({
  selector: 'jhi-user-management-update',
  templateUrl: './user-management-update.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  imports: [FindLanguageFromKeyPipe, TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class UserManagementUpdate implements OnInit {
  languages = LANGUAGES;
  readonly isSaving = signal(false);

  userManagement: any = {
    id: null,
    login: '',
    firstName: null,
    lastName: null,
    email: '',
    activated: true,
    langKey: 'pt-br',
    authorities: [],
  };

  readonly authorities = computed(() => this.authorityService.authorities().map(authority => authority.name));

  protected readonly authorityService = inject(AuthorityService);
  private readonly userService = inject(UserManagementService);
  private readonly route = inject(ActivatedRoute);

  constructor() {
    this.authorityService.authoritiesParams.set({});
  }

  ngOnInit(): void {
    this.route.data.subscribe(({ userManagement }) => {
      if (userManagement) {
        this.userManagement = userManagement;
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    if (this.userManagement.id === null) {
      this.userService.create(this.userManagement as NewUserManagement).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.userService.update(this.userManagement as IUserManagement).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  private onSaveSuccess(): void {
    this.isSaving.set(false);
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving.set(false);
  }
}
