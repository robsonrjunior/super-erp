## Context

The login screen currently uses Bootstrap 5 classes exclusively (`form-control`, `btn btn-primary`, `form-check`, `alert alert-danger`) while the rest of the application uses PrimeNG Aura theme components. The main layout (`main.html`) wraps all routes in a PrimeNG `p-card` and unconditionally renders the navbar. On the login screen, the navbar shows navigation links (Home, Entities, Administration) that are inaccessible to unauthenticated users, adding clutter to the login experience.

## Goals / Non-Goals

**Goals:**
- Achieve visual consistency between the login screen and the rest of the PrimeNG Aura-themed application
- Reduce unnecessary UI elements on the login page by hiding the navbar
- Use PrimeNG form components (InputText, Button, Checkbox, IconField/InputIcon) for the login form
- Use PrimeNG Message component for authentication error feedback
- Preserve all i18n translation keys, `data-cy` test attributes, and existing authentication logic

**Non-Goals:**
- Changing the authentication flow or backend API (`api/authenticate`)
- Modifying i18n translation files
- Restructuring the route configuration
- Adding or removing form fields
- Adding a PrimeNG Password component with toggle visibility (out of scope — still plain password input)
- Changing the forgot password and register links beyond wrapping them in appropriate containers

## Decisions

### Decision 1: Navbar visibility controlled via route listener in main layout
Use Angular's `Router` to detect the current route and conditionally render the navbar with an `@if` block in `main.html`, rather than using a named outlet configuration or CSS hiding.

**Rationale**: The `@if` approach with a route listener signal is the most Angular-idiomatic way to conditionally show/hide a component. CSS hiding (`display: none`) would still compile and render the component unnecessarily. Named outlet manipulation would require route-level changes that affect the entire routing hierarchy.

### Decision 2: Login component imports its own PrimeNG modules
The `login.ts` standalone component will import `InputTextModule`, `ButtonModule`, `CheckboxModule`, `IconFieldModule`, `InputIconModule`, and `MessageModule` directly, rather than adding them to a shared module.

**Rationale**: The login component is a standalone Angular component. Loading PrimeNG modules directly on it keeps imports explicit and avoids adding to `app.config.ts` or creating a shared module. `CardModule` is already available from `main.ts`.

### Decision 3: PrimeNG Card inside the main layout card, not replacing it
The login form will use its own `p-card` inside the `main` content area, nested within the existing app shell `p-card`. The outer `p-card` provides the full-page Aura surface background; the inner `p-card` gives the login form a raised card appearance in the center.

**Rationale**: The app shell card is responsible for the full-height surface background per the `primeng-app-shell` spec. Adding a second card for the login form creates visual depth (card on card) consistent with PrimeNG form patterns, without breaking the app shell constraint.

### Decision 4: Preserve data-cy attributes on new PrimeNG components
All existing `data-cy` attributes (`loginTitle`, `loginError`, `username`, `password`, `submit`, `forgetYourPasswordSelector`) will be preserved on the new PrimeNG components.

**Rationale**: These are required for Cypress e2e tests to continue working without modification. PrimeNG components support arbitrary attributes via attribute binding.

### Decision 5: Use PrimeNG severity-based Message instead of Bootstrap alert
The authentication error will use `p-message severity="error"` with inline text, rather than `alert alert-danger`.

**Rationale**: The PrimeNG Message component provides the same role (error display) with Aura theme styling. It supports `closable` and severity variants out of the box. The forgot password and register links will keep their existing alert-like containers but can use simpler wrapper divs since the `alert-warning` Bootstrap class is no longer needed.

## Risks / Trade-offs

- **[Risk] Cypress e2e tests may break**: Tests that rely on Bootstrap CSS selectors (e.g., `.form-control`, `.btn-primary`) will need updating to match PrimeNG component DOM structure. → **Mitigation**: Preserve `data-cy` attributes; update any failing selector references during implementation.

- **[Risk] PrimeNG InputText value binding differences**: Two-way binding with `[(ngModel)]` on `pInputText` may behave differently than on native `<input>` with `form-control`. → **Mitigation**: This is a well-supported Angular pattern — PrimeNG `InputText` extends native input behavior.

- **[Risk] Layout shift on login page after navbar removal**: Removing the navbar changes the vertical space on the login page, potentially affecting the centered card positioning. → **Mitigation**: The card is already flexbox-centered in the content area; minor CSS adjustments in `login.scss` will handle alignment.

- **[Trade-off] Inner card creates visual depth**: Nesting a `p-card` inside the app shell `p-card` means two card surfaces. This is the intended PrimeNG pattern for centered forms but may look different from the current flat login page. → **Acceptable**: This is the desired outcome — visual consistency with PrimeNG practices.
