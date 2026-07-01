## Context

The navbar currently uses:
- **Bootstrap CSS classes** (`navbar`, `navbar-expand-md`, `nav-link`, `dropdown-menu`, etc.) for layout
- **ng-bootstrap** directives (`ngbDropdown`, `ngbDropdownToggle`, `ngbDropdownMenu`, `ngbCollapse`) for dropdown and collapse behavior
- **FontAwesome** icons throughout
- **Bootstrap responsive toggler** for mobile hamburger menu

The footer and login screen already use PrimeNG components (`p-selectButton`, `p-card`, `p-button`, `p-inputtext`, etc.). The navbar is the last major layout component still using Bootstrap/ng-bootstrap. Dark mode applies `var(--p-primary-300)` / `var(--p-primary-200)` to links, which resolves to green tones from the Aura emerald palette — visually inconsistent with an ERP application.

## Goals / Non-Goals

**Goals:**
- Replace ng-bootstrap dropdowns (`ngbDropdown`) with PrimeNG `p-menu` in popup mode triggered by `p-button` elements
- Replace Bootstrap navbar classes with a custom flexbox layout styled via PrimeNG CSS variables
- Replace FontAwesome icons with PrimeIcons (`pi` classes) in the navbar
- Remove green link color from dark mode by replacing `var(--p-primary-300/200)` with neutral high-contrast colors (`#adb5bd` / `#ced4da`)
- Remove the language dropdown section from the navbar (language switching stays in the footer)
- Add a centered `p-iconfield` + `p-inputtext` search input between the logo and menu items
- Preserve all JHipster needle markers (`jhipster-needle-add-entity-to-menu`, `jhipster-needle-add-element-to-admin-menu`)
- Maintain responsive behavior (hamburger toggle on mobile)
- Keep `account`, `isNavbarCollapsed`, `inProduction`, `openAPIEnabled` signals and auth gating logic intact

**Non-Goals:**
- Implementing search logic / API integration (input is visual placeholder only)
- Changing the footer or theme toggle
- Altering which menu items appear or their routing targets
- Moving dark mode toggle from footer to navbar
- Changing entity or admin menu structure

## Decisions

### 1. PrimeNG Menu component: `p-menu` in popup mode

**Chosen:** Use `p-menu` with `[popup]="true"` triggered by `p-button` elements, laid out in a custom flexbox navbar.

**Alternatives considered:**
- `p-menubar`: Provides horizontal bar + built-in responsive hamburger, but doesn't support a "center" slot for the search input and forces a rigid layout.
- `p-tieredMenu`: Supports cascading submenus (not needed) and adds complexity for simple dropdown lists.
- Keeping ng-bootstrap: No — the whole point is to migrate everything to PrimeNG for visual consistency with the Aura theme.

**Rationale:** Custom flexbox layout with `p-menu[popup]` gives full control over the navbar structure (left logo, center search, right menu buttons), uses PrimeNG theming, and keeps the component tree simple.

### 2. Dark mode link color: neutral gray instead of green primary

**Chosen:** Replace `.dark-mode a { color: var(--p-primary-300) }` with `color: #adb5bd` and `:hover { color: #ced4da }`.

**Alternatives considered:**
- Overriding `--p-primary-*` tokens globally: Would change ALL primary-colored elements (buttons, inputs, etc.), not just links.
- Creating custom CSS variables (`--app-link-dark`, `--app-link-hover-dark`): Adds indirection without benefit since these are single-use values.
- Using `var(--p-surface-*)` tokens: Surface tokens are background colors, not text colors, and may not provide sufficient contrast.

**Rationale:** Direct neutral gray values (`#adb5bd` / `#ced4da`) provide high contrast on dark backgrounds without the green tint. These are Bootstrap-standard secondary colors that look professional in any context.

### 3. Search input placement: centered between logo and menu items

**Chosen:** Place the search input as the second flex child in a three-section layout (`logo | search | menu`), using `flex: 1` with `justify-content: center` on the search container.

**Rationale:** This mirrors common enterprise application patterns (e.g., GitHub, GitLab, Jira) where the search bar is centrally positioned. The search area can expand/contract with available space while logo and menu items remain at the edges.

### 4. Icons: switch from FontAwesome to PrimeIcons

**Chosen:** Replace `fa-icon` with `<i class="pi pi-*"></i>` for all navbar icons.

**Rationale:** PrimeIcons are already loaded globally (`primeicons.css` in `angular.json`) and used in the footer and login screen. Using them in the navbar eliminates the FontAwesome dependency from this component and ensures visual consistency with other PrimeNG components.

### 5. Responsive behavior: custom toggle with `isNavbarCollapsed` signal

**Chosen:** Keep the existing `isNavbarCollapsed` signal pattern but replace `ngbCollapse` with a conditional `@if (!isNavbarCollapsed())` + CSS transition.

**Rationale:** The collapse signal already works correctly. Replacing `ngbCollapse` with Angular's native `@if` preserves the behavior without requiring an additional PrimeNG component. A CSS `max-height` transition on the menu container provides the animation.

## Risks / Trade-offs

- [Complexity] Entity dropdown has 15 items + admin has 5 items → `p-menu` model arrays in TypeScript will be verbose but manageable. **Mitigation:** Keep menu model definition in the component class, organized as readonly properties.
- [JHipster compatibility] Needle markers (`<!-- jhipster-needle-... -->`) are HTML comments expected in the template → `p-menu` uses a TypeScript model, not template HTML. **Mitigation:** The needle markers must remain in the template file as comments near where the JHipster generator expects them. Even though PrimeNG menus use model arrays, we'll place the markers as comments in the template section adjacent to the menu setup code.
- [Mobile UX] Three-section layout (logo / search / menu) may be cramped on narrow screens → **Mitigation:** On small screens, hide the search input and show only logo + hamburger toggle. The search can be revealed when the menu is expanded on mobile.
- [Accessibility] PrimeNG `p-menu[popup]` uses `Overlay` with `appendTo="body"` → menu DOM is detached from the navbar. This is expected behavior and does not affect keyboard navigation since PrimeNG handles focus management internally.
