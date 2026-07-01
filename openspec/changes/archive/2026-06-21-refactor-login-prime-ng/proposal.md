## Why

The login screen currently uses Bootstrap-only styling while the rest of the application uses PrimeNG Aura theme components. This creates visual inconsistency and a disjointed user experience. Additionally, the navbar renders on the login screen even though its navigation items (Home, Entities, Administration) are inaccessible to unauthenticated users.

## What Changes

- Remove the top navbar from the login screen (it serves no purpose for unauthenticated users)
- Wrap the login form inside a PrimeNG Card component for visual consistency with the app shell
- Replace Bootstrap `form-control` inputs with PrimeNG InputText components
- Replace the Bootstrap submit button with a PrimeNG Button component
- Replace the Bootstrap form-check checkbox with a PrimeNG Checkbox component
- Add PrimeNG IconField/InputIcon to the username field (user icon) and password field (lock icon)
- Replace Bootstrap `alert` divs with PrimeNG Message components for the authentication error
- Preserve all existing i18n translation keys, `data-cy` test selectors, and form validation behavior
- Preserve the forgot password and register links

## Capabilities

### New Capabilities
- `login-screen-prime-ng`: The login screen UI refactored to use PrimeNG components (Card, InputText, Button, Checkbox, IconField/InputIcon, Message) instead of Bootstrap, providing visual consistency with the PrimeNG Aura-themed app shell.

### Modified Capabilities
- `primeng-app-shell`: The main layout SHALL conditionally hide the navbar on the login route (`/login`), showing only the PrimeNG card wrapper and footer.

## Impact

- **Frontend files**: `login.ts`, `login.html`, `layouts/main/main.ts`, `layouts/main/main.html`, `layouts/main/main.scss` (new: `login.scss`)
- **PrimeNG modules**: CardModule (already imported), InputTextModule, ButtonModule, CheckboxModule, IconFieldModule, InputIconModule, MessageModule (all need importing in login component)
- **i18n**: No changes — all existing translation keys remain
- **Auth flow**: No changes — authentication logic unchanged
- **Backend**: No impact
- **Tests**: May need to update Cypress e2e tests if they rely on Bootstrap-specific CSS selectors
