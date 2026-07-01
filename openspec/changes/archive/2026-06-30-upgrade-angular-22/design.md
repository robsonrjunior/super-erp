## Context

The project is a JHipster 9 monolith currently on Angular 21.2.x with Angular Material/CDK 21.2.x. Angular 22 is the next stable major release. Since the project is only one major version behind, the upgrade surface is manageable — primarily a dependency version bump with potential adjustments for removed or changed APIs.

The frontend lives at the repo root in `src/main/webapp/app/` but `package.json` is at the repo root (not nested inside webapp). Builds use `./npmw` which wraps Gradle-managed Node/npm.

## Goals / Non-Goals

**Goals:**
- Upgrade all `@angular/*` packages to 22.x stable
- Upgrade `@angular/material` and `@angular/cdk` to 22.x stable
- Ensure all existing features, tests, linting, and builds pass
- Maintain compatibility with PrimeNG, ng-bootstrap, ngx-translate, and dayjs

**Non-Goals:**
- Refactoring components or services beyond what the upgrade strictly requires
- Upgrading other unrelated dependencies (those will be separate changes)
- Adding new features or changing application behavior
- Upgrading the Java/Spring Boot backend

## Decisions

### Decision 1: Use `ng update` for automated migration
**Choice:** Run `ng update @angular/core@22 @angular/cli@22 @angular/material@22 @angular/cdk@22` via the Angular CLI update workflow.

**Rationale:** Angular's `ng update` command automatically updates `package.json`, runs migration schematics for breaking changes, and adjusts configuration files (`angular.json`, `tsconfig*.json`). This reduces manual error risk.

**Alternatives considered:**
- Manual version bump in `package.json` + `npm install` — error-prone, misses built-in migration schematics
- JHipster upgrade sub-generator — JHipster 9 may not yet support Angular 22; safer to use native Angular tooling

### Decision 2: Upgrade TypeScript alongside Angular
**Choice:** Update TypeScript to the version required by Angular 22 (expected 5.5.x or 5.6.x) as part of this change.

**Rationale:** Angular 22 will declare its TypeScript peer dependency range. The Angular CLI update schematics should handle this automatically.

### Decision 3: Verify but don't upgrade third-party Angular libraries
**Choice:** Check PrimeNG, ng-bootstrap, ngx-translate, and dayjs for Angular 22 compatibility. Only upgrade them if they lack Angular 22 support.

**Rationale:** Minimizing change scope reduces risk. Third-party package upgrades bring their own breaking changes. Only force-upgrade if the current version is incompatible with Angular 22.

### Decision 4: `@angular-eslint` package alignment
**Choice:** Upgrade `@angular-eslint/*` packages to the version compatible with Angular 22.

**Rationale:** ESLint rules and schematics are tightly coupled to the Angular compiler version. Mismatched versions cause lint errors or false positives.

## Risks / Trade-offs

| Risk | Mitigation |
|------|------------|
| Angular 22 introduces breaking API changes not handled by migration schematics | Review Angular 22 changelog and migration guide before upgrading; search codebase for deprecated v21 APIs |
| Third-party libraries (PrimeNG, ng-bootstrap) lack Angular 22 support at upgrade time | Pin those packages to their current version and test; if incompatible, defer the full upgrade or contribute compatibility fixes |
| `@angular/service-worker` behavior changes could break PWA caching | Verify service worker registration in production build; test offline behavior |
| ESLint rules from `@angular-eslint` change between versions | Run `./npmw run lint` after upgrade and address any new violations |
| Build tooling (`@angular/build`, esbuild) behavior differences | Test both dev and production builds; check output bundle sizes for regressions |

## Migration Plan

1. **Pre-upgrade checkpoint**: Commit current state, ensure baseline `./npmw test` and `./npmw run lint` pass cleanly
2. **Execute upgrade**: Run `ng update` commands for core, CLI, and Material
3. **Address migration failures**: Fix any source or config changes the schematics couldn't handle automatically
4. **Verify**: Run `./npmw test`, `./npmw run lint`, `./npmw run start`, and production build
5. **Rollback plan**: Revert to pre-upgrade commit if critical issues arise; the change is fully contained in frontend dependencies and config
