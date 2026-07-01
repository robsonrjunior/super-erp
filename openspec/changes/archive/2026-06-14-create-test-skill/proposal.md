## Why

Running tests in this project requires knowing the right incantations — Gradle for backend (`./gradlew test integrationTest`), npm wrapper for frontend (`./npmw test`), and skipping webapp builds when running backend-only. An opencode skill captures this knowledge so the AI can run the right test commands without guessing or asking.

## What Changes

- Create a new opencode skill at `.opencode/skills/test/SKILL.md` that teaches the AI how to run the project's tests
- The skill covers both backend (JUnit + integration tests via Gradle) and frontend (Vitest via npmw)
- Includes the `-x webapp -x webapp_test` pattern for backend-only runs
- Covers linting, format checking, and production-quality checks
- Documents Gradle profile flags (`-Pprod`, `-Pe2e`) for different test scenarios

## Capabilities

### New Capabilities

- `test-skill`: An opencode skill that provides the AI with the exact commands and conventions needed to run tests, linting, and quality checks across the full JHipster 9 stack.

### Modified Capabilities

None. This is a new tooling capability with no changes to existing specs.

## Impact

- New file: `.opencode/skills/test/SKILL.md`
- No changes to application code, build files, or existing tests
- The skill is loaded by opencode and surfaced to the AI model when the user asks to run tests
