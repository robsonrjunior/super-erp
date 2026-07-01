## Why

Users have no way to switch between light and dark appearance. The app currently uses a static hybrid look (dark navbar/footer, light content area). Adding a dark/light toggle improves accessibility, reduces eye strain in low-light environments, and aligns with modern user expectations.

## What Changes

- Add a theme toggle button to the footer, displayed before the language selector
- Display sun icon when in light mode, moon icon when in dark mode, followed by descriptive text
- Persist the user's preference in `localStorage` and apply it on page load
- Configure PrimeNG's `providePrimeNG` with dark mode selector support
- Update footer styles so the toggle integrates visually with the existing `bg-dark` footer
- Add translations for `pt-br`, `en`, and `es` (e.g., "Light mode" / "Modo claro" / "Modo claro")

## Capabilities

### New Capabilities

- `dark-mode-toggle`: Users can toggle between light and dark themes via a button in the footer. The current mode is persisted across sessions and applied automatically on page load.

### Modified Capabilities

<!-- None -->

## Impact

- **Footer component** (`src/main/webapp/app/layouts/footer/`): HTML, TS, and SCSS updated to include the toggle button
- **App config** (`src/main/webapp/app/app.config.ts`): PrimeNG theme configuration updated with dark mode selector
- **Global styles** (`src/main/webapp/content/scss/global.scss`): Body background and link colors made theme-aware
- **Main layout** (`src/main/webapp/app/layouts/main/main.scss`): Remove hardcoded white backgrounds
- **i18n files** (`src/main/webapp/i18n/{pt-br,en,es}/global.json`): New translation keys for light/dark mode labels
- **Navbar** (`src/main/webapp/app/layouts/navbar/`): Updated to respond to the active theme class instead of hardcoded `navbar-dark bg-dark`

## Non-goals

- Full CSS refactoring of every hardcoded color in the application
- Per-component theme overrides beyond what PrimeNG tokens provide
- System `prefers-color-scheme` auto-detection (can be added later)
- Changing the loading screen or index.html meta theme color dynamically
