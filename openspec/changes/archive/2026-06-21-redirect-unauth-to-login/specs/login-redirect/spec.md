## ADDED Requirements

### Requirement: Unauthenticated users are redirected to login
The system SHALL redirect unauthenticated users who navigate to the root path (`/`) directly to the login screen (`/login`) instead of displaying the home page.

#### Scenario: Unauthenticated user opens the app
- **WHEN** an unauthenticated user navigates to `/`
- **THEN** the system redirects them to `/login`

#### Scenario: Authenticated user opens the app
- **WHEN** an authenticated user navigates to `/`
- **THEN** the system displays the home page

#### Scenario: User logs in and returns to home
- **WHEN** an unauthenticated user opens `/`, gets redirected to `/login`, and successfully authenticates
- **THEN** the system redirects them back to `/` and displays the home page
