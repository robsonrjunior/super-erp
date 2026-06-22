# Dark Mode Toggle

## Purpose

Allow users to switch between light and dark themes via a toggle button in the footer. The current mode is persisted in `localStorage` and applied on page load. Themes are implemented via PrimeNG's token-based system with a `dark-mode` CSS class on `<html>` serving as the single source of truth.

## Requirements

### Requirement: Theme toggle button in footer
The footer SHALL display a theme toggle button before the language selector. The button SHALL show a sun icon (`pi pi-sun`) when in light mode and a moon icon (`pi pi-moon`) when in dark mode, followed by descriptive text from i18n translations.

#### Scenario: Default state is light mode
- **WHEN** the application loads for the first time with no preference stored in `localStorage`
- **THEN** the toggle button displays a sun icon and the text "Modo escuro" (pt-br), "Dark mode" (en), or "Modo oscuro" (es)

#### Scenario: Switching from light to dark mode
- **WHEN** the user clicks the toggle button while in light mode
- **THEN** the button displays a moon icon and the text "Modo claro" (pt-br), "Light mode" (en), or "Modo claro" (es)
- **AND** the `dark-mode` class and `data-bs-theme="dark"` attribute are added to the `<html>` element
- **AND** PrimeNG components render with dark theme tokens
- **AND** the preference `dark-mode: "true"` is saved to `localStorage`

#### Scenario: Switching from dark to light mode
- **WHEN** the user clicks the toggle button while in dark mode
- **THEN** the button displays a sun icon and the text "Modo escuro" (pt-br), "Dark mode" (en), or "Modo oscuro" (es)
- **AND** the `dark-mode` class and `data-bs-theme="dark"` attribute are removed from the `<html>` element
- **AND** PrimeNG components render with light theme tokens
- **AND** the preference `dark-mode: "false"` is saved to `localStorage`

### Requirement: Theme preference persistence
The user's theme preference SHALL be persisted in `localStorage` under the key `dark-mode` with value `"true"` (dark) or `"false"` (light). The preference SHALL be applied on page load before the Angular application renders visible content.

#### Scenario: Restoring dark mode on page load
- **WHEN** the user has previously selected dark mode (`localStorage` key `dark-mode` is `"true"`)
- **AND** the application loads
- **THEN** the `<html>` element has the `dark-mode` class and `data-bs-theme="dark"` attribute
- **AND** the toggle button displays the moon icon

#### Scenario: Restoring light mode on page load
- **WHEN** the user has previously selected light mode (`localStorage` key `dark-mode` is `"false"`)
- **AND** the application loads
- **THEN** the `<html>` element does NOT have the `dark-mode` class or `data-bs-theme="dark"` attribute
- **AND** the toggle button displays the sun icon

### Requirement: Global styles respond to theme
Global page styles SHALL update when the theme changes. The body background, link color, and footer border SHALL switch between light and dark values based on the `dark-mode` class on `<html>`. In dark mode, links SHALL use neutral high-contrast colors (`#adb5bd` for normal state, `#ced4da` for hover state) instead of the PrimeNG primary palette to avoid green-tinted links.

#### Scenario: Body background in dark mode
- **WHEN** dark mode is active
- **THEN** the page body background is a dark color (e.g., `var(--p-surface-950)` or `#212529`)

#### Scenario: Link color in dark mode
- **WHEN** dark mode is active
- **THEN** links render with color `#adb5bd` (a neutral gray, not green)
- **AND** links on hover render with color `#ced4da`

#### Scenario: Body background in light mode
- **WHEN** light mode is active
- **THEN** the page body background is white (`#fff`)

### Requirement: I18n translations for theme toggle
The application SHALL provide translations for the theme toggle label in all three supported languages: `pt-br`, `en`, and `es`.

#### Scenario: Portuguese translation
- **WHEN** the current language is `pt-br`
- **THEN** the toggle button text reads "Modo escuro" (when light) or "Modo claro" (when dark)

#### Scenario: English translation
- **WHEN** the current language is `en`
- **THEN** the toggle button text reads "Dark mode" (when light) or "Light mode" (when dark)

#### Scenario: Spanish translation
- **WHEN** the current language is `es`
- **THEN** the toggle button text reads "Modo oscuro" (when light) or "Modo claro" (when dark)
