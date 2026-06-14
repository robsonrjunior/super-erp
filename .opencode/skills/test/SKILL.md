---
name: test
description: Run tests, linting, and quality checks for the JHipster 9 monolith. Covers backend (Gradle/JUnit), frontend (Vitest), ESLint, CheckStyle, Prettier, and full production quality checks. USE WHEN the user asks to run tests, execute tests, check code, lint, run linting, verify formatting, or run quality checks.
---

# Test

This project uses a dual build system: Gradle for the Spring Boot backend and `./npmw` (npm wrapper managed by Gradle) for the Angular frontend.

## Backend tests

```bash
# Unit tests only
./gradlew test -x webapp -x webapp_test

# Unit + integration tests
./gradlew test integrationTest -x webapp -x webapp_test
```

The `-x webapp -x webapp_test` flags skip the Node/frontend build, which is unnecessary for backend-only test runs.

## Frontend tests

```bash
# Vitest with coverage (includes pretest lint)
./npmw test

# Watch mode
./npmw run test:watch
```

## Linting

```bash
# ESLint (frontend)
./npmw run lint

# CheckStyle (backend main sources)
./gradlew checkstyleMain -x webapp -x webapp_test

# No-http checkstyle
./gradlew checkstyleNohttp -x webapp -x webapp_test
```

## Format checking

```bash
# Prettier format check
./npmw run prettier:check
```

## Full production quality

```bash
# Tests + static analysis + coverage report (prod profile)
./gradlew -Pprod clean check jacocoTestReport -x webapp -x webapp_test
```
