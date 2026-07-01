## Why

Page content currently stretches edge-to-edge across the full viewport width with no horizontal breathing room. This harms readability on wide screens and looks unpolished compared to modern web applications that constrain content width.

## What Changes

- Add horizontal padding/max-width constraints to the `.app-content` area so page content has left and right margins
- Add an inner container wrapper inside the navbar to constrain its content while keeping the full-width primary background bar
- Add an inner container wrapper inside the footer to constrain its content while keeping the full-width footer bar
- Login route remains unaffected (navbar is already hidden, and login page already centers its card)

## Capabilities

### New Capabilities
- `content-container-width`: Constrains page content, navbar content, and footer content to a centered container with horizontal margins, while preserving full-width background bars for navbar and footer

### Modified Capabilities
<!-- No existing spec-level requirements change. Adding constrained width is a new behavior not covered by existing specs. -->

## Impact

- Affected files: `main.scss`, `main.html`, `navbar.html`, `navbar.scss`, `footer.html`, `footer.scss` (all under `src/main/webapp/app/layouts/`)
- Frontend-only change — no backend, API, or database impact
- No dependency changes
- No i18n impact
- No JHipster needle markers affected

## Non-goals

- Changing the login page layout (already centered via its own card)
- Modifying entity-specific page templates or individual component styles
- Adding responsive breakpoint-specific container widths (single fixed max-width for simplicity)
