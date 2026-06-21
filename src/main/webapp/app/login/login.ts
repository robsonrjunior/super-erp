import { AfterViewInit, Component, ElementRef, OnInit, inject, signal, viewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';

import { TranslateModule } from '@ngx-translate/core';

import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { TranslateDirective } from 'app/shared/language';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'jhi-login',
  imports: [
    TranslateDirective,
    TranslateModule,
    FormsModule,
    RouterLink,
    InputTextModule,
    ButtonModule,
    CheckboxModule,
    IconFieldModule,
    InputIconModule,
    MessageModule,
    CardModule,
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export default class Login implements OnInit, AfterViewInit {
  username = viewChild.required<ElementRef>('username');

  readonly authenticationError = signal(false);

  loginForm = { username: '', password: '', rememberMe: false };

  private readonly accountService = inject(AccountService);
  private readonly loginService = inject(LoginService);
  private readonly router = inject(Router);

  ngOnInit(): void {
    // if already authenticated then navigate to home page
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  ngAfterViewInit(): void {
    this.username().nativeElement.focus();
  }

  login(): void {
    this.loginService.login(this.loginForm).subscribe({
      next: () => {
        this.authenticationError.set(false);
        if (!this.router.currentNavigation()) {
          // There were no routing during login (eg from navigationToStoredUrl)
          this.router.navigate(['']);
        }
      },
      error: () => this.authenticationError.set(true),
    });
  }
}
