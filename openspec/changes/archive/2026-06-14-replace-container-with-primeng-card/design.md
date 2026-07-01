## Context

The application currently uses a Bootstrap-based layout shell in `MainComponent` (`src/main/webapp/app/layouts/main/`). The structure is:

```
div.app-layout (flexbox column, min-height: 100vh)
  ├── router-outlet[name="navbar"]  → Bootstrap navbar
  ├── main.app-content (flex: 1)
  │   └── div.card.jh-card          → Bootstrap card wrapper
  │       └── router-outlet          → page content
  └── app-footer                     → Bootstrap footer with PrimeNG p-selectButton
```

PrimeNG is already configured with the Aura theme preset via `providePrimeNG()` in `app.config.ts`. The PrimeNG `CardModule` is available from the `primeng` package (`^21.1.7`).

## Goals / Non-Goals

**Goals:**
- Replace the Bootstrap `.card.jh-card` wrapper with a PrimeNG `p-card` that wraps the entire app shell (navbar, content, footer)
- Use the PrimeNG card surface background as the application's background color
- Maintain the sticky-footer behavior (footer at bottom when content is short)
- Keep the dev page ribbon and all existing functionality intact

**Non-Goals:**
- Changing the navbar component (remains ng-bootstrap-based)
- Changing the footer component (already uses PrimeNG `p-selectButton`)
- Converting other Bootstrap components to PrimeNG
- Modifying the Aura theme configuration

## Decisions

### Decision 1: Wrap entire layout in `p-card`, not just content area

**Chosen**: Place a single `p-card` as the outermost container inside `jhi-main`, wrapping navbar + content + footer.

**Rationale**: The user wants the entire screen inside the card so the card's background color becomes the app background. Placing the card only around the content area would not achieve this — the navbar and footer would sit outside the card.

**Alternatives considered**:
- Apply PrimeNG theme background color to `body` via CSS — simpler but couples CSS to a specific theme variable; the `p-card` approach is semantic and theme-agnostic.
- Use multiple `p-card` components (one for navbar, one for content) — adds unnecessary visual separation.

### Decision 2: Remove `.app-layout` flexbox and use card body for layout

**Chosen**: Remove the `.app-layout` wrapper div. Use `p-card` with a flexbox column layout on its content area to position navbar, content, and footer vertically.

**Rationale**: The `p-card` provides its own container semantics. The `.app-layout` was a flexbox column that enforced `min-height: 100vh` and pushed the footer down. The same behavior can be achieved with CSS on the card or card body.

### Decision 3: Use CSS `min-height: 100vh` on `p-card` or `:host`

**Chosen**: Apply `min-height: 100vh` and flexbox column layout on the `:host` (which is `<jhi-main>`) so the card fills the viewport.

**Rationale**: The `p-card` component renders as a styled `<div>` with inner `<div>` elements for header/body. Setting flexbox on `:host` ensures the card stretches to full height regardless of PrimeNG's internal DOM structure.

### Decision 4: Card header for nav, no card footer (use body for everything)

**Chosen**: Place the navbar in the card header slot (`ng-template pTemplate="header"`), the router outlet (content) and footer in the card body.

**Rationale**: The PrimeNG card has header/body/footer sections. The navbar naturally fits in the header area. The content and footer go in the body to allow the content area to flex-grow and push the footer down. Using the PrimeNG card footer section would not allow flex-grow behavior for the content area.

**Alternative**: Put navbar and footer in card body as well, with flexbox — simpler structure, fewer template slots. **Selected this instead** — keeps the implementation simpler and avoids the visual border between card header and body.

### Decision 5: Keep navbar and footer as-is, only change the wrapper

**Chosen**: Do not modify `navbar` or `footer` components. Only change `main.html`, `main.ts`, and `main.scss`.

**Rationale**: Minimal change surface. The navbar and footer already work correctly. This change is purely about the wrapping container.

## Risks / Trade-offs

- **p-card header/content border**: If using separate header/body/footer sections, PrimeNG renders a border between them, which may not be desired. → Mitigation: Use a single body section with flexbox layout, avoid separate header/footer slots.
- **Bootstrap utility classes in main.html**: The current `main.html` uses Bootstrap classes (`container-fluid`, `card`, `jh-card`). → Mitigation: These are replaced entirely; no conflict.
- **Mobile responsiveness**: The current layout relies on Bootstrap's responsive utilities. → Mitigation: PrimeNG Aura is responsive by default; flexbox layout works on all viewport sizes.

## Open Questions

<!-- None — all decisions are clear. -->
