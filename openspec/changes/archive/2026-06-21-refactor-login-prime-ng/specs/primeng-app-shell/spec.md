# Primeng App Shell

Delta spec for hiding the navbar on the login route.

## ADDED Requirements

### Requirement: Navbar hidden on login route
The navbar SHALL NOT be displayed when the user is on the login route (`/login`).

#### Scenario: Navbar hidden on login page
- **WHEN** an unauthenticated user navigates to `/login`
- **THEN** the navbar is not rendered in the viewport
- **AND** the page ribbon, PrimeNG card wrapper, and footer remain visible

#### Scenario: Navbar visible on non-login routes
- **WHEN** the user navigates to any route other than `/login`
- **THEN** the navbar is rendered at the top of the page inside the PrimeNG card wrapper
