## Context

The project has a dual build system: Gradle for the Spring Boot backend and the `./npmw` wrapper for the Angular frontend. Test commands differ between them, and there are Gradle-specific flags for excluding frontend builds when running backend-only tests. The AI currently has this information only indirectly through AGENTS.md — a dedicated skill makes it first-class tooling.

The skill follows opencode's skill convention: a `SKILL.md` file with YAML frontmatter inside `.opencode/skills/test/`. It is auto-discovered by opencode's skill loader.

## Goals / Non-Goals

**Goals:**
- Provide exact commands for running backend tests, frontend tests, linting, and quality checks
- Cover both unit tests and integration tests
- Cover Gradle profile flags for different environments (dev, prod, e2e)
- Include the `-x webapp -x webapp_test` pattern for backend-only runs

**Non-Goals:**
- Writing new tests or modifying existing test suites
- Changing the build system or test frameworks
- Docker-based or CI/CD test orchestration (those live in npm scripts)
- Replacing AGENTS.md — this complements it

## Decisions

1. **Skill file location**: `.opencode/skills/test/SKILL.md` — follows opencode's project-skill convention (`SKILL.md` inside a named folder under `.opencode/skills/`). This is auto-discovered with no config changes needed.

2. **Skill scope**: Covers only the `run`/`execute` aspect of tests — not test authoring patterns. The description front-loads trigger keywords (`test`, `run tests`, `execute tests`, `check`, `lint`) so the model surfaces it when the user mentions these.

3. **Commands to cover**:
   - Backend unit + integration: `./gradlew test integrationTest` (with `-x webapp -x webapp_test` for backend-only)
   - Frontend: `./npmw test` (Vitest with coverage)
   - Lint: `./npmw run lint` (ESLint), `./gradlew checkstyleNohttp -x webapp -x webapp_test`, `./gradlew checkstyleMain`
   - Format check: `./npmw run prettier:check`
   - Full quality: `./gradlew -Pprod clean check jacocoTestReport -x webapp -x webapp_test`

4. **No config changes to `opencode.json`**: The skill loader automatically scans `.opencode/skills/`, so no `skills.paths` declaration is needed. The `$schema` reference is not needed for skills.

## Risks / Trade-offs

- **[Staleness]**: If test commands change (e.g., Gradle task rename, new test framework), the skill becomes outdated → Mitigation: skill is a single file easy to spot and update
- **[Overlap with AGENTS.md]**: Some commands are duplicated between AGENTS.md and this skill → Acceptable: AGENTS.md is for human developers reading the repo, the skill is for the AI model
