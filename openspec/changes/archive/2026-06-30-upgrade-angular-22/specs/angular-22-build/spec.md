## ADDED Requirements

### Requirement: Angular CLI 22 and build toolchain
The project SHALL use `@angular/cli` 22.x, `@angular/build` 22.x, and `@angular/compiler-cli` 22.x for building, serving, and testing the frontend.

#### Scenario: CLI version is 22.x
- **WHEN** `npx ng version` is executed
- **THEN** the Angular CLI version SHALL be 22.x

#### Scenario: Production build succeeds
- **WHEN** `./npmw run build` (production build) is executed
- **THEN** the build SHALL complete without errors

#### Scenario: Development server starts
- **WHEN** `./npmw run start` is executed
- **THEN** the Angular dev server SHALL start successfully and serve the application

### Requirement: Build configuration compatibility
The `angular.json` and `tsconfig*.json` files SHALL be updated to match any schema changes required by Angular 22.

#### Scenario: Build configuration is valid
- **WHEN** `npx ng build --dry-run` (or equivalent validation) is executed
- **THEN** no configuration validation errors SHALL occur

### Requirement: ESLint compatibility
ESLint SHALL continue to pass with Angular 22, using compatible versions of `@angular-eslint/*` packages.

#### Scenario: ESLint passes after upgrade
- **WHEN** `./npmw run lint` is executed
- **THEN** ESLint SHALL complete without new errors introduced by the upgrade
