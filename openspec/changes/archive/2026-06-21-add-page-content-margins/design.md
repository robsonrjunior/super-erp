## Context

The current layout has no horizontal constraints on page content — `.app-content` fills the full viewport width. The navbar and footer also span full width with no inner container. Bootstrap 5 is already available (imported in `vendor.scss`) and provides `.container` / `.container-fluid` classes, but none are currently used in the layout shell.

## Goals / Non-Goals

**Goals:**
- Constrain main page content to a centered container with horizontal margins on left and right
- Add an inner container inside the navbar to constrain its content while keeping the primary-colored bar full-width
- Add an inner container inside the footer to constrain its content while keeping the footer bar full-width
- Preserve all existing layout behavior (sticky footer, login route no-navbar, responsive mobile navbar, dev ribbon)

**Non-Goals:**
- Changing the login page layout (already centered via its own card)
- Modifying entity-specific page templates
- Adding responsive breakpoint-specific container widths
- Introducing new npm dependencies

## Decisions

### Decision 1: Use CSS `max-width` + `margin: auto` instead of Bootstrap `.container`

**Rationale:** Bootstrap's `.container` class adds responsive breakpoints with fixed widths at each breakpoint (e.g., 540px, 720px, 960px, 1140px, 1320px). For an ERP application with data tables and forms, this causes layout jumps as the viewport crosses breakpoints. A single `max-width` with fluid behavior below that threshold gives a smoother experience and ensures content uses available space efficiently.

**Alternative considered:** Bootstrap `.container` — simpler to implement but causes responsive width jumps. Rejected because ERP data tables and forms benefit from using full available width up to the max.

### Decision 2: Apply max-width at the layout level, not individual pages

**Rationale:** Modifying `main.scss` (`app-content`) and the navbar/footer templates ensures all pages get consistent margins without touching dozens of entity templates. Individual pages can override if needed.

**Alternative considered:** Adding containers to each entity template individually — rejected because it's repetitive and error-prone.

### Decision 3: CSS custom property for max-width

**Rationale:** Using a CSS custom property (`--app-content-max-width`) allows overriding in one place and is consistent with the project's existing pattern of CSS custom properties for theming (`--app-body-bg`, `--app-shell-bg`).

**Alternative considered:** Hardcoded `max-width` value — rejected because it's harder to tweak later.

### Decision 4: Inner container divs for navbar and footer

**Rationale:** Adding a `<div class="container-content">` child inside the navbar and footer keeps the parent's full-width background styling (primary color for navbar, togglebutton background for footer) while constraining only the content. This avoids breaking the existing background color bars.

## Risks / Trade-offs

- **Wide tables may overflow on smaller screens** → Tables already use Bootstrap's responsive table pattern; entity tables naturally wrap within the constrained width
- **Future entity templates added via JHipster will automatically inherit the margins** → This is the intended behavior
- **The login page already centers its card** → No risk of double-wrapping since login content is self-contained in a p-card with its own centering
