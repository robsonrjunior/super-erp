## ADDED Requirements

### Requirement: Main content area has constrained width
The `.app-content` area SHALL have a maximum width applied via CSS, centered horizontally within the viewport.

#### Scenario: Content centered with margins on desktop
- **WHEN** viewing the application on a screen wider than the max-width
- **THEN** the main content area is horizontally centered with equal left and right margins
- **AND** the page background (app shell) remains visible in the margin areas

#### Scenario: Content uses full width on narrow screens
- **WHEN** viewing the application on a screen narrower than the max-width
- **THEN** the main content area fills the full available width without horizontal margins

### Requirement: Navbar has inner content container
The navbar SHALL have an inner container element that constrains its content width while the navbar bar itself SHALL remain full-width with its primary color background.

#### Scenario: Navbar bar spans full width
- **WHEN** viewing the application on a non-login route
- **THEN** the navbar background bar spans the full viewport width
- **AND** uses the PrimeNG primary color

#### Scenario: Navbar content constrained to container
- **WHEN** viewing the application on a screen wider than the max-width
- **THEN** the navbar content (brand, search, menu buttons, account button) is centered within the same max-width container used by the main content

### Requirement: Footer has inner content container
The footer SHALL have an inner container element that constrains its content width while the footer bar itself SHALL remain full-width.

#### Scenario: Footer bar spans full width
- **WHEN** viewing any route
- **THEN** the footer background bar spans the full viewport width

#### Scenario: Footer content constrained to container
- **WHEN** viewing the application on a screen wider than the max-width
- **THEN** the footer content (app name, copyright, theme toggle, language selector) is centered within the same max-width container used by the main content

### Requirement: Login page layout unaffected
The login page SHALL NOT be affected by the new content width constraints.

#### Scenario: Login page unchanged
- **WHEN** navigating to `/login`
- **THEN** the login card remains centered on the page as before
- **AND** no additional margins or containers are introduced on the login page

### Requirement: Sticky footer behavior preserved
The footer SHALL remain at the bottom of the viewport when content is short, and pushed below content when content is long.

#### Scenario: Footer at bottom with short content
- **WHEN** navigating to a page with minimal content
- **THEN** the footer is positioned at the bottom of the viewport
- **AND** the footer's inner container is horizontally centered

#### Scenario: Footer pushed down with long content
- **WHEN** navigating to a page with content taller than the viewport
- **THEN** the footer appears after the content ends
- **AND** the footer's inner container is horizontally centered
