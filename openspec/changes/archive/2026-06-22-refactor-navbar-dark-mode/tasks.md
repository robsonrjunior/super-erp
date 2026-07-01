## 1. Fix dark mode link colors

- [x] 1.1 Replace `var(--p-primary-300)` with `#adb5bd` and `var(--p-primary-200)` with `#ced4da` in `.dark-mode a` and `.dark-mode a:hover` selectors in `src/main/webapp/content/scss/global.scss`

## 2. Add i18n translations for search placeholder

- [x] 2.1 Add `"global.menu.search": "Pesquisar..."` to `src/main/webapp/i18n/pt-br/global.json`
- [x] 2.2 Add `"global.menu.search": "Search..."` to `src/main/webapp/i18n/en/global.json`
- [x] 2.3 Add `"global.menu.search": "Buscar..."` to `src/main/webapp/i18n/es/global.json`

## 3. Refactor navbar TypeScript component

- [x] 3.1 Remove FontAwesome module, ng-bootstrap, ActiveMenuDirective, FindLanguageFromKeyPipe imports from `navbar.ts`
- [x] 3.2 Add PrimeNG imports: `MenuModule`, `ButtonModule`, `IconFieldModule`, `InputTextModule`, `InputIconModule`, `BadgeModule` (if needed for mobile pill)
- [x] 3.3 Remove `changeLanguage()` method, `languages` signal, and `StateStorageService` / `TranslateService` injections (no longer needed in navbar)
- [x] 3.4 Define entity menu items array as a readonly `MenuItem[]` property in the component class
- [x] 3.5 Define admin menu items array as a readonly `MenuItem[]` property (guarded by authority via template conditional)
- [x] 3.6 Define account menu items array as a readonly `MenuItem[]` property
- [x] 3.7 Keep `account`, `isNavbarCollapsed`, `inProduction`, `openAPIEnabled`, `version` signals and `collapseNavbar()`, `login()`, `logout()` methods

## 4. Rewrite navbar HTML template

- [x] 4.1 Replace Bootstrap navbar structure with custom flexbox layout: `<nav>` with three sections (brand | search | menu)
- [x] 4.2 Left section: logo image, app title, version — use `<a routerLink="/">` with existing logo elements, replace `<fa-icon icon="bars">` with `<i class="pi pi-bars">` for mobile toggle
- [x] 4.3 Center section: `p-iconfield` wrapping `p-inputicon` (search icon) + `p-inputtext` with translated placeholder and a CSS class for centering
- [x] 4.4 Right section: menu buttons container — replace `ngbDropdown` structures with `p-button` (styled as nav links) + `p-menu[popup]` for each dropdown
- [x] 4.5 Implement entity menu as `<p-menu #entityMenu [model]="entityMenuItems" [popup]="true" />` triggered by a `p-button` with `(click)="entityMenu.toggle($event)"` and `[disabled]="account() === null"`
- [x] 4.6 Implement admin menu similarly, gated by `*jhiHasAnyAuthority="'ROLE_ADMIN'"` on the button element
- [x] 4.7 Implement account menu similarly, with conditional model items based on `account() !== null`
- [x] 4.8 Remove the language dropdown section entirely (lines 293-315 of current `navbar.html`)
- [x] 4.9 Add mobile responsive classes: hide search and menu buttons on small screens, show hamburger; use `@if (!isNavbarCollapsed())` for mobile menu visibility
- [x] 4.10 Preserve JHipster needle markers as comments in the template near the model definitions

## 5. Update navbar SCSS

- [x] 5.1 Replace Bootstrap-dependent selectors (`.navbar`, `.nav-link`, `.dropdown-menu`) with custom flexbox styling
- [x] 5.2 Use PrimeNG CSS variables (`--p-surface-card`, `--p-text-muted-color`) for background and text colors
- [x] 5.3 Add styles for the centered search input wrapper (flexbox centering, responsive width)
- [x] 5.4 Add styles for menu buttons to match the look of the original nav links (no border, transparent background, Aura token colors)
- [x] 5.5 Add mobile breakpoint styles (`@media (max-width: 767px)`) for collapsed menu layout

## 6. Verification

- [x] 6.1 Run ESLint: `./npmw run lint` — fix any lint errors
- [x] 6.2 Run frontend tests: `./npmw test` — verify no regressions (pre-existing infra issue: Node v24 ESM, no test files exist)
- [x] 6.3 Run TypeScript compilation check: `./npmw run build` — confirm no type errors
- [x] 6.4 Manually verify: navbar renders with PrimeNG components, search input visible, language section removed, dark mode links are neutral gray (not green), mobile hamburger works
