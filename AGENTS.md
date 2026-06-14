# AGENTS.md

## Project summary

JHipster 9 monolith — Spring Boot 4 (Java 21) backend + Angular 21 frontend, PostgreSQL with Liquibase, JWT auth, Gradle build.

## Commands

```bash
# Use ./npmw, not npm (wraps Gradle-managed local Node/npm)
./npmw install       # after dependency changes
./npmw run start     # Angular dev server (port 4200)
./npmw run backend:start   # Spring Boot dev (port 8080)
./npmw run watch     # both together (concurrently)

# Tests
./gradlew test integrationTest     # backend unit + ITs
./npmw test                         # frontend unit (Vitest)

# Linting
./npmw run lint                     # ESLint
./gradlew checkstyleNohttp -x webapp -x webapp_test  # no-http checkstyle
./gradlew checkstyleMain            # CheckStyle (only main sources)

# Production build
./gradlew -Pprod clean bootJar     # fat jar
./gradlew -Pprod clean check jacocoTestReport sonarqube -Dsonar.login=admin -Dsonar.password=admin
```

## Project structure quirks

- **Frontend root**: `src/main/webapp/` — Angular source lives here, not at repo root.
- **Build output**: `build/resources/main/static/` — backend serves compiled frontend from here.
- **Custom esbuild plugins** in `build-plugins/` (i18n + env defines). `build-plugins/package.json` uses `"type": "commonjs"`.
- **Angular component prefix**: `jhi` (enforced by ESLint rule).
- **Gradle profile flags**: `-Pprod` for production, `-Pwar` for WAR packaging, `-Ptls` for TLS, `-Pe2e` for e2e.
- **Gradle tasks exclude webapp**: use `-x webapp -x webapp_test` when running backend-only tasks to skip Node/frontend builds.
- **npm scripts reference config vars** via `$npm_package_config_*` (e.g. `$npm_package_config_default_environment`).
- **`./gradlew` default task** is `bootRun` (starts Spring Boot on dev profile).

## Domain conventions (see `openspec/specs/`)

- **Soft-deletable entities**: `Tenant`, `Supplier`, `Customer`, `Person`, `Company`, `Product`, `RawMaterial`, `Warehouse`, `StockMovement`, `Sale`, `SaleItem` extend `SoftDeletableEntity` (MappedSuperclass with `deletedAt`). Do NOT declare `deletedAt` directly on these entities.
- **Entity methods**: Lombok `@Getter`/`@Setter` for simple fields. Hand-written methods only for bidirectional `@OneToMany`/`@ManyToMany` collection synchronization (custom `setXxx(Set)`, `addXxx()`, `removeXxx()`). No fluent builder-style setters returning `this`.
- **Reference entities** (`Country`, `State`, `City`) do NOT have soft-delete.

## Code style

- **Prettier**: 140 print width, single quotes, 2-space indent. Java: 4-space indent (`prettier-plugin-java`).
- **ESLint**: `curly` always, `eqeqeq` always, `arrow-body-style` error, `no-console` (allow warn/error only). TypeScript code in `src/main/webapp/app/**` uses strict type checked.
- **Lint-staged** on pre-commit: Prettier formatting only (runs via `./npmw` husky hook).
- **Lombok** for getters/setters/builders — don't write them manually.
- **Liquibase** for schema changes — ASCII timestamps in changelog filenames.

## Key libraries & framework

- **Backend**: JHipster Framework 9.0.0, Spring Boot 4.0.3, OAuth2 Resource Server (JWT), MapStruct 1.6.3, lombok, testcontainers + PostgreSQL for ITs.
- **Frontend**: Angular 21, PrimeNG (Aura theme), ng-bootstrap, ngx-translate, dayjs. Auth interceptors in `app/core/interceptor/`.
- **I18n**: `pt-br` (native), `en`, `es`. Translations in `src/main/webapp/i18n/`.

## Generated code

- Lines with `jhipster-needle-*` comments are insertion points for JHipster code generation — do not remove these markers.
- Entity config files in `.jhipster/` and the `erp-core.jdl` file define the domain model.
