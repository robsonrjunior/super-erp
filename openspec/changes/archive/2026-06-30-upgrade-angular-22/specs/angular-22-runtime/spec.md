## ADDED Requirements

### Requirement: Angular 22 runtime core packages
The project SHALL use Angular 22.x stable for all runtime packages: `@angular/animations`, `@angular/common`, `@angular/compiler`, `@angular/core`, `@angular/forms`, `@angular/localize`, `@angular/platform-browser`, `@angular/router`, and `@angular/service-worker`.

#### Scenario: All runtime packages resolve to 22.x
- **WHEN** `npm ls @angular/core` is executed
- **THEN** the resolved version SHALL be `^22.0.0` or later 22.x stable

#### Scenario: Application compiles successfully with Angular 22
- **WHEN** `./npmw run start` (dev build) is executed
- **THEN** the Angular application SHALL compile without errors related to missing or incompatible Angular core APIs

### Requirement: TypeScript compatibility with Angular 22
The project SHALL use a TypeScript version compatible with Angular 22 (5.5.x or later as required by the Angular 22 release).

#### Scenario: TypeScript meets Angular 22 minimum version
- **WHEN** `npx tsc --version` is executed
- **THEN** the TypeScript version SHALL be 5.5.x or later

### Requirement: Existing behavior preservation
All existing application features SHALL continue to function identically after the upgrade. No user-visible behavior changes, regressions in routing, form handling, HTTP interceptors, i18n, or service worker functionality are permitted.

#### Scenario: All frontend tests pass
- **WHEN** `./npmw test` is executed
- **THEN** all Vitest unit tests SHALL pass without modification to test logic

#### Scenario: i18n continues working
- **WHEN** the application loads with any supported locale (pt-br, en, es)
- **THEN** translations SHALL render correctly via ngx-translate

#### Scenario: Service worker registers correctly
- **WHEN** the production build is deployed and accessed
- **THEN** the `@angular/service-worker` SHALL register and function as before the upgrade
