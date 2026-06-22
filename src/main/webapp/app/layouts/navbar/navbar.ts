import { Component, OnInit, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { MenuItem } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { MenuModule } from 'primeng/menu';

import { environment } from 'environments/environment';

import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import HasAnyAuthorityDirective from 'app/shared/auth/has-any-authority.directive';
import { TranslateDirective } from 'app/shared/language';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
  imports: [
    RouterLink,
    ButtonModule,
    IconFieldModule,
    InputIconModule,
    InputTextModule,
    MenuModule,
    HasAnyAuthorityDirective,
    TranslateDirective,
    TranslateModule,
  ],
})
export default class Navbar implements OnInit {
  readonly inProduction = signal(true);
  readonly isNavbarCollapsed = signal(true);
  readonly openAPIEnabled = signal(false);
  readonly version: string;
  readonly account = inject(AccountService).account;

  entityMenuItems: MenuItem[] = [];
  adminMenuItems: MenuItem[] = [];
  loggedOutMenuItems: MenuItem[] = [];
  loggedInMenuItems: MenuItem[] = [];

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

  private buildMenus(): void {
    const t = (key: string): string => this.translateService.instant(key) as string;

    this.entityMenuItems = [
      { label: t('global.menu.entities.tenant'), icon: 'pi pi-building', routerLink: '/tenant', command: () => this.collapseNavbar() },
      { label: t('global.menu.entities.country'), icon: 'pi pi-globe', routerLink: '/country', command: () => this.collapseNavbar() },
      { label: t('global.menu.entities.state'), icon: 'pi pi-map', routerLink: '/state', command: () => this.collapseNavbar() },
      { label: t('global.menu.entities.city'), icon: 'pi pi-map-marker', routerLink: '/city', command: () => this.collapseNavbar() },
      { label: t('global.menu.entities.supplier'), icon: 'pi pi-truck', routerLink: '/supplier', command: () => this.collapseNavbar() },
      { label: t('global.menu.entities.customer'), icon: 'pi pi-users', routerLink: '/customer', command: () => this.collapseNavbar() },
      { label: t('global.menu.entities.person'), icon: 'pi pi-user', routerLink: '/person', command: () => this.collapseNavbar() },
      { label: t('global.menu.entities.company'), icon: 'pi pi-building', routerLink: '/company', command: () => this.collapseNavbar() },
      { label: t('global.menu.entities.product'), icon: 'pi pi-box', routerLink: '/product', command: () => this.collapseNavbar() },
      {
        label: t('global.menu.entities.rawMaterial'),
        icon: 'pi pi-cog',
        routerLink: '/raw-material',
        command: () => this.collapseNavbar(),
      },
      {
        label: t('global.menu.entities.warehouse'),
        icon: 'pi pi-building',
        routerLink: '/warehouse',
        command: () => this.collapseNavbar(),
      },
      {
        label: t('global.menu.entities.stockMovement'),
        icon: 'pi pi-exchange',
        routerLink: '/stock-movement',
        command: () => this.collapseNavbar(),
      },
      { label: t('global.menu.entities.sale'), icon: 'pi pi-shopping-cart', routerLink: '/sale', command: () => this.collapseNavbar() },
      { label: t('global.menu.entities.saleItem'), icon: 'pi pi-tag', routerLink: '/sale-item', command: () => this.collapseNavbar() },
      {
        label: t('global.menu.entities.adminAuthority'),
        icon: 'pi pi-shield',
        routerLink: '/authority',
        command: () => this.collapseNavbar(),
      },
      {
        label: t('userManagement.home.title'),
        icon: 'pi pi-user-edit',
        routerLink: '/user-management',
        command: () => this.collapseNavbar(),
      },
    ];

    this.loggedOutMenuItems = [
      { label: t('global.menu.account.login'), icon: 'pi pi-sign-in', command: () => this.login() },
      {
        label: t('global.menu.account.register'),
        icon: 'pi pi-user-plus',
        routerLink: '/account/register',
        command: () => this.collapseNavbar(),
      },
    ];

    this.loggedInMenuItems = [
      {
        label: t('global.menu.account.settings'),
        icon: 'pi pi-wrench',
        routerLink: '/account/settings',
        command: () => this.collapseNavbar(),
      },
      {
        label: t('global.menu.account.password'),
        icon: 'pi pi-lock',
        routerLink: '/account/password',
        command: () => this.collapseNavbar(),
      },
      { label: t('global.menu.account.logout'), icon: 'pi pi-sign-out', command: () => this.logout() },
    ];

    this.buildAdminMenu();
  }

  private buildAdminMenu(): void {
    const t = (key: string): string => this.translateService.instant(key) as string;

    const items: MenuItem[] = [
      {
        label: t('global.menu.admin.metrics'),
        icon: 'pi pi-chart-bar',
        routerLink: '/admin/metrics',
        command: () => this.collapseNavbar(),
      },
      {
        label: t('global.menu.admin.health'),
        icon: 'pi pi-heart',
        routerLink: '/admin/health',
        command: () => this.collapseNavbar(),
      },
      {
        label: t('global.menu.admin.configuration'),
        icon: 'pi pi-cog',
        routerLink: '/admin/configuration',
        command: () => this.collapseNavbar(),
      },
      { label: t('global.menu.admin.logs'), icon: 'pi pi-list', routerLink: '/admin/logs', command: () => this.collapseNavbar() },
    ];

    if (this.openAPIEnabled()) {
      items.push({
        label: t('global.menu.admin.apidocs'),
        icon: 'pi pi-book',
        routerLink: '/admin/docs',
        command: () => this.collapseNavbar(),
      });
    }

    this.adminMenuItems = items;
  }
}
