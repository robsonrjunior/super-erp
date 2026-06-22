# Primeng Navbar

## Purpose

The application navbar uses only PrimeNG components — no Bootstrap/ng-bootstrap. It provides entity, admin, and account dropdowns via `p-menu[popup]`, a centered search input placeholder, and responsive mobile behavior. Language switching is exclusively in the footer.

## Requirements

### Requirement: Navbar uses PrimeNG components
The navbar SHALL use only PrimeNG components for all interactive elements. No ng-bootstrap directives (`ngbDropdown`, `ngbDropdownToggle`, `ngbDropdownMenu`, `ngbCollapse`) or Bootstrap CSS utility classes (`navbar`, `nav-link`, `dropdown-menu`, `dropdown-item`, `dropdown-toggle`) SHALL be present in the navbar template or styles.

#### Scenario: No ng-bootstrap imports in navbar
- **WHEN** inspecting the navbar component TypeScript file
- **THEN** no imports from `@ng-bootstrap/ng-bootstrap` are present

#### Scenario: No Bootstrap navbar CSS classes
- **WHEN** inspecting the navbar template
- **THEN** no elements use `class="navbar"`, `class="nav-link"`, or `class="dropdown-menu"`

### Requirement: Menu dropdowns use PrimeNG popup menus
Entity, admin, and account dropdown menus SHALL be rendered using PrimeNG `p-menu` components in popup mode, triggered by `p-button` elements styled as navbar links.

#### Scenario: Entity menu is a PrimeNG popup
- **WHEN** an authenticated user clicks the "Entities" button in the navbar
- **THEN** a PrimeNG popup menu appears listing all entity links (Tenant, Country, State, City, Supplier, Customer, Person, Company, Product, Raw Material, Warehouse, Stock Movement, Sale, Sale Item, Authority, User Management)
- **AND** each item navigates to the correct route on click

#### Scenario: Admin menu gated by authority
- **WHEN** a user without ROLE_ADMIN is authenticated
- **THEN** the Admin menu button is not visible in the navbar
- **WHEN** a user with ROLE_ADMIN is authenticated
- **THEN** the Admin menu button is visible and shows admin links (Metrics, Health, Configuration, Logs, API)

#### Scenario: Account menu shows login state
- **WHEN** a user is not authenticated
- **THEN** the account menu shows Login and Register options
- **WHEN** a user is authenticated
- **THEN** the account menu shows Settings, Password, and Logout options

### Requirement: Language section removed from navbar
The navbar SHALL NOT contain a language selection dropdown. Language switching SHALL remain available exclusively in the footer.

#### Scenario: No language dropdown in navbar
- **WHEN** inspecting the navbar template
- **THEN** no element with id "languagesnavBarDropdown" exists
- **AND** no language-related menu items are rendered

### Requirement: Search input placeholder in navbar
The navbar SHALL display a search input field centered between the brand/logo section and the menu buttons. The input SHALL use PrimeNG `p-iconfield`, `p-inputtext`, and `p-inputicon` components with a search icon. No search logic SHALL be implemented — the input is a visual placeholder only.

#### Scenario: Search input visible on desktop
- **WHEN** viewing the application on a screen wider than 768px
- **THEN** a search input with a magnifying glass icon is visible in the center of the navbar
- **AND** the input uses PrimeNG styling (Aura theme tokens)

#### Scenario: Search input has placeholder text
- **WHEN** the search input is rendered
- **THEN** it displays a translated placeholder (e.g., "Pesquisar..." in pt-br, "Search..." in en, "Buscar..." in es)

### Requirement: Responsive mobile behavior preserved
The navbar SHALL collapse its menu items on small screens and display a hamburger toggle button. The collapse behavior SHALL use the existing `isNavbarCollapsed` signal.

#### Scenario: Hamburger toggle on mobile
- **WHEN** viewing the application on a screen narrower than 768px
- **THEN** menu buttons are hidden by default
- **AND** a hamburger toggle button is visible
- **WHEN** clicking the hamburger toggle
- **THEN** menu buttons become visible

#### Scenario: Navbar items expand on desktop
- **WHEN** viewing the application on a screen wider than 768px
- **THEN** all navbar items (logo, search, menu buttons) are visible without toggling

### Requirement: Navbar hidden on login route
The navbar SHALL NOT be displayed when the user is on the login route (`/login`).

#### Scenario: Navbar hidden on login page
- **WHEN** an unauthenticated user navigates to `/login`
- **THEN** the navbar is not rendered in the viewport

#### Scenario: Navbar visible on non-login routes
- **WHEN** the user navigates to any route other than `/login`
- **THEN** the navbar is rendered at the top of the page

### Requirement: JHipster needle markers preserved
The navbar template SHALL retain the JHipster needle marker comments `<!-- jhipster-needle-add-entity-to-menu -->` and `<!-- jhipster-needle-add-element-to-admin-menu -->` to allow future entity generation.

#### Scenario: Entity needle marker present
- **WHEN** inspecting the navbar template
- **THEN** the comment `<!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->` exists in the file

#### Scenario: Admin needle marker present
- **WHEN** inspecting the navbar template
- **THEN** the comment `<!-- jhipster-needle-add-element-to-admin-menu - JHipster will add entities to the admin menu here -->` exists in the file

### Requirement: Navbar icons use PrimeIcons
All icons in the navbar SHALL use PrimeIcons (`<i class="pi pi-*"></i>`) instead of FontAwesome (`<fa-icon icon="...">`). The FontAwesome module SHALL be removed from the navbar imports.

#### Scenario: No FontAwesome imports in navbar
- **WHEN** inspecting the navbar component TypeScript file
- **THEN** no imports from `@fortawesome/angular-fontawesome` are present

#### Scenario: PrimeIcons used for menu items
- **WHEN** inspecting the navbar template
- **THEN** menu button icons use `<i class="pi pi-...">` syntax

### Requirement: Navbar background uses primary color
The navbar background SHALL use the PrimeNG primary color token (`var(--p-primary-color)`) with text rendered in the contrast color (`var(--p-primary-contrast-color)`). Hover and active states on nav buttons SHALL use semi-transparent white overlays for contrast on the primary background.

#### Scenario: Navbar renders with primary background
- **WHEN** the application loads on a non-login route
- **THEN** the navbar background is the PrimeNG primary color
- **AND** navbar text is the primary contrast color (typically white)

### Requirement: Account button positioned at right corner
The account menu button SHALL be positioned at the far right corner of the navbar, separated from the entity and admin menu buttons. It SHALL remain visible on all screen sizes.

#### Scenario: Account button at right corner on desktop
- **WHEN** viewing the application on a screen wider than 768px
- **THEN** the account button is positioned at the far right edge of the navbar
- **AND** entity and admin buttons are positioned to its left

#### Scenario: Account button visible on mobile
- **WHEN** viewing the application on a screen narrower than 768px
- **THEN** the account button is still visible in the right corner alongside the hamburger toggle
