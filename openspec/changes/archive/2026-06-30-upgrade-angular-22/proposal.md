## Why

Angular 22 brings performance improvements, new features, and long-term support. Upgrading now keeps the project current with the latest stable release while the version gap is small (21 → 22), minimizing migration effort and preventing accumulation of breaking changes across multiple skipped versions.

## What Changes

- Upgrade all `@angular/*` packages from `^21.2.x` to `^22.0.0` (or latest 22.x stable)
- Upgrade `@angular/material` and `@angular/cdk` from `^21.2.x` to `^22.0.0`
- Update Angular CLI builder (`@angular/build`, `@angular/cli`) to 22.x
- Adjust any deprecated APIs removed in v22 (e.g., component/standalone bootstrap changes)
- Update TypeScript version if required by Angular 22
- Verify compatibility with third-party dependencies (PrimeNG, ng-bootstrap, ngx-translate, dayjs)
- Run full test suite and linting after upgrade

## Capabilities

### New Capabilities

- `angular-22-runtime`: The Angular 22 runtime core (compiler, common, core, forms, platform-browser, router, animations, localize, service-worker) — updated to v22.x stable
- `angular-22-material`: Angular Material 22 and CDK 22 — updated component library and behavioral primitives
- `angular-22-build`: Angular CLI 22, `@angular/build` 22, and compiler-cli 22 — updated build toolchain

### Modified Capabilities

<!-- No existing spec-level requirement changes. This is a dependency version bump; existing behavior is preserved. -->

## Impact

- **Affected dependencies**: `package.json` (all `@angular/*` packages, potentially `typescript`, `zone.js`, and related dev dependencies)
- **Source code**: Potential adjustments for removed/deprecated v22 APIs in `src/main/webapp/app/**`
- **Build configuration**: `angular.json`, `tsconfig*.json` if schema or option changes are required
- **Third-party compat**: PrimeNG, ng-bootstrap, ngx-translate, dayjs — verify they support Angular 22
- **Tests**: All Vitest frontend unit tests need to pass after the upgrade
- **Linting/formatting**: ESLint and Prettier checks must pass with updated tooling
