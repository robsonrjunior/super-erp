## ADDED Requirements

### Requirement: AI can discover and load the test skill

The system SHALL provide an opencode skill at `.opencode/skills/test/SKILL.md` that is auto-discovered by opencode's skill loader and surfaced to the AI model when the user mentions test execution, quality checks, or linting.

The skill file SHALL:
- Be named `SKILL.md` inside the `.opencode/skills/test/` directory
- Include YAML frontmatter with `name: test` and a `description` that front-loads trigger keywords (run tests, execute tests, check, lint)
- Contain Markdown body with exact commands for running tests across the full stack

#### Scenario: Skill is auto-discovered by opencode

- **WHEN** opencode starts with the project's `.opencode/` directory present
- **THEN** the test skill at `.opencode/skills/test/SKILL.md` is loaded and available to the AI model

#### Scenario: Skill is surfaced on relevant user prompts

- **WHEN** the user says "run the tests" or "check the code" or "lint the project"
- **THEN** the AI model receives the test skill content and uses the correct commands from it

### Requirement: Backend test commands must be documented

The test skill SHALL document the exact Gradle command for running all backend tests, including unit tests and integration tests, with the appropriate exclusions for skipping frontend builds.

#### Scenario: Running all backend tests

- **WHEN** the AI needs to run backend tests
- **THEN** the skill provides the command `./gradlew test integrationTest -x webapp -x webapp_test`

#### Scenario: Running only unit tests

- **WHEN** the AI needs to run backend unit tests without integration tests
- **THEN** the skill provides the command `./gradlew test -x webapp -x webapp_test`

### Requirement: Frontend test command must be documented

The test skill SHALL document the exact npm wrapper command for running frontend tests using Vitest, including the coverage flag.

#### Scenario: Running frontend tests

- **WHEN** the AI needs to run frontend tests
- **THEN** the skill provides the command `./npmw test`

#### Scenario: Watching frontend tests

- **WHEN** the AI needs to run frontend tests in watch mode
- **THEN** the skill provides the command `./npmw run test:watch`

### Requirement: Linting commands must be documented

The test skill SHALL document commands for running all linters (ESLint for frontend, CheckStyle for backend, no-http checkstyle).

#### Scenario: Running frontend lint

- **WHEN** the AI needs to run frontend linting
- **THEN** the skill provides the command `./npmw run lint`

#### Scenario: Running backend lint

- **WHEN** the AI needs to run backend linting
- **THEN** the skill provides the commands `./gradlew checkstyleMain -x webapp -x webapp_test` and `./gradlew checkstyleNohttp -x webapp -x webapp_test`

### Requirement: Format check command must be documented

The test skill SHALL document the Prettier format check command.

#### Scenario: Checking code formatting

- **WHEN** the AI needs to verify code formatting
- **THEN** the skill provides the command `./npmw run prettier:check`

### Requirement: Production quality checks must be documented

The test skill SHALL document the combined command for running all quality checks including tests and static analysis.

#### Scenario: Running full quality checks for production

- **WHEN** the AI needs to run the full production-quality check pipeline
- **THEN** the skill provides the command `./gradlew -Pprod clean check jacocoTestReport -x webapp -x webapp_test`
