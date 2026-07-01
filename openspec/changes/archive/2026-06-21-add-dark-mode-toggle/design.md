## Context

The application currently has no dark/light mode switching. The footer is always styled with Bootstrap's `bg-dark text-white`, and body background is hardcoded to `#fff`. PrimeNG's Aura theme is configured in `providePrimeNG` without a dark mode selector. Users are locked into a static hybrid appearance.

The proposed change adds a toggle button in the footer (before the language selector) that switches between light and dark modes. The preference is persisted in `localStorage` and applied on page load.

## Goals / Non-Goals

**Goals:**
- Toggle button in the footer with sun (light mode) / moon (dark mode) icons followed by descriptive text
- Mode persisted in `localStorage` and restored on page load
- PrimeNG Aura theme switches between light and dark based on the selected mode
- Translations for `pt-br`, `en`, `es`
- Footer, navbar, and global page background respond to the active theme

**Non-Goals:**
- Full application-wide CSS refactoring of every hardcoded color
- Auto-detection of system `prefers-color-scheme`
- Dynamic `<meta name="theme-color">` update
- Per-component theme overrides

## Decisions

### 1. Use a CSS class on `<html>` as the dark mode trigger

**Decision:** Toggle a `dark-mode` class on the `<html>` element. Use Bootstrap's `data-bs-theme` attribute as well for Bootstrap 5 dark mode support.

**Rationale:** PrimeNG's `providePrimeNG` supports a `darkModeSelector` option (e.g., `.dark-mode`). Bootstrap 5 supports `data-bs-theme="dark"` on any element. A single class on `<html>` serves as the single source of truth.

**Alternatives considered:**
- Using a service with an Observable — more complex, harder to integrate with CSS-based theme frameworks.
- Using CSS `prefers-color-scheme` media query only — removes user choice.

### 2. Toggle logic stays in the Footer component, not a separate service

**Decision:** Keep the dark mode state and toggle logic within the Footer component. Read/write `localStorage` key `dark-mode` (value `"true"` | `"false"`).

**Rationale:** The toggle button lives in the footer. A separate service adds indirection without benefit for a single consumer. If multiple components need the state later, extract a service then.

**Alternatives considered:**
- A dedicated `ThemeService` — premature abstraction for one toggle.

### 3. Use Angular's Renderer2 to manipulate `<html>` class and attribute

**Decision:** Use `Renderer2` via Angular's `@Inject(DOCUMENT)` to add/remove the `dark-mode` class and `data-bs-theme` attribute on `<html>`.

**Rationale:** Avoids direct DOM manipulation (`document.documentElement.classList`), which is safer in Angular Universal/server-side scenarios and follows Angular best practices.

### 4. Icons from PrimeIcons (already a dependency)

**Decision:** Use `pi pi-sun` and `pi pi-moon` from PrimeIcons (already included at `v7.0.0`).

**Rationale:** No new dependency needed. Both icons are available in the `v7.0.0` release included in the project.

**Alternatives considered:**
- Bootstrap Icons — already included but `bi-sun-fill`/`bi-moon-fill` are less semantically appropriate.
- Custom SVG — unnecessary overhead.

### 5. Translation keys: `global.json`

**Decision:** Add keys under `footer.dark.mode` (for the label text) in each language's `global.json`.

| Key | pt-br | en | es |
|---|---|---|---|
| `footer.dark.mode` | Modo escuro | Dark mode | Modo oscuro |

**Rationale:** Follows existing key naming convention (`footer.*` prefix since the text is in the footer). The text describes the destination mode (what clicking will activate), which is the standard UX pattern.

### 6. PrimeNG dark mode selector configuration

**Decision:** Add `options: { darkModeSelector: '.dark-mode' }` to the `providePrimeNG` call in `app.config.ts`.

**Rationale:** PrimeNG will watch for the `.dark-mode` class on `<html>` and automatically switch its internal token values between light and dark palettes.

## Risks / Trade-offs

- **[Risk] Bootstrap components may look inconsistent in dark mode** since Bootstrap's `data-bs-theme="dark"` only affects Bootstrap-styled elements, not PrimeNG components. → **Mitigation:** Both mechanisms are triggered simultaneously (`.dark-mode` class + `data-bs-theme="dark"` attribute), so PrimeNG and Bootstrap styles switch together.

- **[Risk] Hardcoded colors in `global.scss` (`#fff`, `#533f03`, `rgba(0,0,0,0.125)`) won't update** — they remain static regardless of mode. → **Mitigation:** Override the most visible hardcoded values (body background, link color, footer border) to use CSS custom properties that respond to `.dark-mode`. Document remaining static colors as technical debt.

- **[Risk] Flash of wrong theme on page load** — the default is light mode; if the user prefers dark, the page renders light before the Angular app boots and reads `localStorage`. → **Mitigation:** Acceptable for v1. A blocking `<script>` in `<head>` could prevent the flash but is deferred as future work.
