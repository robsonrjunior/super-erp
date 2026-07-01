import { Component, OnInit, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { TranslateModule, TranslateService } from '@ngx-translate/core';

import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';

import { environment } from 'environments/environment';

import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import HasAnyAuthorityDirective from 'app/shared/auth/has-any-authority.directive';
import { TranslateDirective } from 'app/shared/language';

interface NavMenuItem {
  label: string;
  icon: string;
  routerLink?: string;
}

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
  changeDetection: ChangeDetectionStrategy.Eager,
  imports: [
    RouterLink,
    HasAnyAuthorityDirective,
    TranslateDirective,
    TranslateModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,
  ],
})
export default class Navbar implements OnInit {
  readonly inProduction = signal(true);
  readonly isNavbarCollapsed = signal(true);
  readonly openAPIEnabled = signal(false);
  readonly version: string;
  readonly account = inject(AccountService).account;

  entityMenuItems: NavMenuItem[] = [];
  adminMenuItems: NavMenuItem[] = [];
  loggedOutMenuItems: NavMenuItem[] = [];
  loggedInMenuItems: NavMenuItem[] = [];

  private readonly loginService = inject(LoginService);
  private readonly translateService = inject(TranslateService);
  private readonly profileService = inject(ProfileService);
  private readonly router = inject(Router);

  constructor() {
    const { VERSION } = environment;
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    } else {
      this.version = '';
    }
    this.buildMenus();
    this.translateService.onLangChange.subscribe(() => this.buildMenus());
    this.translateService.onTranslationChange.subscribe(() => this.buildMenus());
  }

  ngOnInit(): void {
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction.set(profileInfo.inProduction ?? true);
      this.openAPIEnabled.set(profileInfo.openAPIEnabled ?? false);
      this.buildAdminMenu();
    });
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed.set(true);
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  handleAccountMenuItem(item: NavMenuItem): void {
    if (!item.routerLink) {
      if (item.icon === 'login') {
        this.login();
      } else if (item.icon === 'logout') {
        this.logout();
      }
    } else {
      this.collapseNavbar();
    }
  }

  private buildMenus(): void {
    const t = (key: string): string => this.translateService.instant(key) as string;

    this.entityMenuItems = [
      { label: t('global.menu.entities.tenant'), icon: 'apartment', routerLink: '/tenant' },
      { label: t('global.menu.entities.country'), icon: 'public', routerLink: '/country' },
      { label: t('global.menu.entities.state'), icon: 'map', routerLink: '/state' },
      { label: t('global.menu.entities.city'), icon: 'location_on', routerLink: '/city' },
      { label: t('global.menu.entities.supplier'), icon: 'local_shipping', routerLink: '/supplier' },
      { label: t('global.menu.entities.customer'), icon: 'people', routerLink: '/customer' },
      { label: t('global.menu.entities.person'), icon: 'person', routerLink: '/person' },
      { label: t('global.menu.entities.company'), icon: 'apartment', routerLink: '/company' },
      { label: t('global.menu.entities.product'), icon: 'inventory_2', routerLink: '/product' },
      { label: t('global.menu.entities.rawMaterial'), icon: 'settings', routerLink: '/raw-material' },
      { label: t('global.menu.entities.warehouse'), icon: 'apartment', routerLink: '/warehouse' },
      { label: t('global.menu.entities.stockMovement'), icon: 'swap_horiz', routerLink: '/stock-movement' },
      { label: t('global.menu.entities.sale'), icon: 'shopping_cart', routerLink: '/sale' },
      { label: t('global.menu.entities.saleItem'), icon: 'sell', routerLink: '/sale-item' },
      { label: t('global.menu.entities.adminAuthority'), icon: 'shield', routerLink: '/authority' },
      { label: t('userManagement.home.title'), icon: 'manage_accounts', routerLink: '/user-management' },
    ];

    this.loggedOutMenuItems = [
      { label: t('global.menu.account.login'), icon: 'login', routerLink: undefined },
      { label: t('global.menu.account.register'), icon: 'person_add', routerLink: '/account/register' },
    ];

    this.loggedInMenuItems = [
      { label: t('global.menu.account.settings'), icon: 'build', routerLink: '/account/settings' },
      { label: t('global.menu.account.password'), icon: 'lock', routerLink: '/account/password' },
      { label: t('global.menu.account.logout'), icon: 'logout', routerLink: undefined },
    ];

    this.buildAdminMenu();
  }

  private buildAdminMenu(): void {
    const t = (key: string): string => this.translateService.instant(key) as string;

    const items: NavMenuItem[] = [
      { label: t('global.menu.admin.metrics'), icon: 'bar_chart', routerLink: '/admin/metrics' },
      { label: t('global.menu.admin.health'), icon: 'favorite', routerLink: '/admin/health' },
      { label: t('global.menu.admin.configuration'), icon: 'settings', routerLink: '/admin/configuration' },
      { label: t('global.menu.admin.logs'), icon: 'list', routerLink: '/admin/logs' },
    ];

    if (this.openAPIEnabled()) {
      items.push({ label: t('global.menu.admin.apidocs'), icon: 'book', routerLink: '/admin/docs' });
    }

    this.adminMenuItems = items;
  }
}
