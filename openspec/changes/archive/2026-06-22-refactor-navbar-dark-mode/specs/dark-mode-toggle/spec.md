## MODIFIED Requirements

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
