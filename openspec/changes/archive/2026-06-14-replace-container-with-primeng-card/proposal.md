## Why

The current application shell uses a Bootstrap card (`.card.jh-card`) inside a flexbox layout (`.app-layout`), with a white background inherited from Bootstrap defaults. Replacing this with a PrimeNG `p-card` provides a cohesive visual shell consistent with the PrimeNG Aura theme already configured in the app, giving the entire application a modern, unified look with the PrimeNG background color as the app background.

## What Changes

- Replace the Bootstrap `.card.jh-card` wrapper in the main layout with a PrimeNG `p-card` component
- The `p-card` wraps the entire application (navbar, content, footer) so its surface background color becomes the app's visual background
- Remove the `.app-layout` flexbox container — the `p-card` handles layout directly
- Ensure the PrimeNG card content area fills the viewport height so the footer stays at the bottom

## Capabilities

### New Capabilities

- `primeng-app-shell`: Wraps the entire application (navbar, routed content, footer) inside a PrimeNG `p-card`, using PrimeNG theme colors as the application background

### Modified Capabilities

<!-- No existing capability requirements change — this is a purely presentational UI update -->

## Impact

- **Affected files**: `src/main/webapp/app/layouts/main/main.html`, `src/main/webapp/app/layouts/main/main.ts`, `src/main/webapp/app/layouts/main/main.scss`
- **Dependencies**: PrimeNG `CardModule` (already available via `primeng` dependency)
- **Breaking**: None — layout structure changes are internal to the `Main` component
- **Theme**: Leverages the existing Aura preset from `@primeuix/themes` already configured in `app.config.ts`
