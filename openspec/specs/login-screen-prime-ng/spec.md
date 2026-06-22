# Login Screen PrimeNG

## Purpose

The login screen SHALL use PrimeNG components (Card, InputText, Button, Checkbox, IconField/InputIcon, Message) instead of Bootstrap classes, providing visual consistency with the PrimeNG Aura-themed application shell.

## Requirements

### Requirement: Login form wrapped in a PrimeNG Card
The login form SHALL be rendered inside a PrimeNG `p-card` component, centered on the page.

#### Scenario: Login page renders with card wrapper
- **WHEN** an unauthenticated user navigates to `/login`
- **THEN** the login form is displayed inside a PrimeNG `p-card` component
- **AND** the card is horizontally centered on the page

### Requirement: PrimeNG InputText for credentials
The username and password fields SHALL use PrimeNG `InputText` components with the `pInputText` directive.

#### Scenario: Username field uses PrimeNG InputText
- **WHEN** the login page is displayed
- **THEN** the username input is rendered as a PrimeNG InputText component
- **AND** the input uses two-way binding with `loginForm.username`
- **AND** the `data-cy="username"` attribute is preserved

#### Scenario: Password field uses PrimeNG InputText
- **WHEN** the login page is displayed
- **THEN** the password input is rendered as a PrimeNG InputText component with `type="password"`
- **AND** the input uses two-way binding with `loginForm.password`
- **AND** the `data-cy="password"` attribute is preserved

### Requirement: PrimeNG IconField with InputIcon for field icons
Each credential field SHALL display a leading icon using PrimeNG `IconField` and `InputIcon` components.

#### Scenario: Username field shows user icon
- **WHEN** the login page is displayed
- **THEN** the username input is wrapped in a PrimeNG `p-iconfield` component
- **AND** a `p-inputicon` with a user icon (e.g., `pi pi-user`) is displayed at the start of the field

#### Scenario: Password field shows lock icon
- **WHEN** the login page is displayed
- **THEN** the password input is wrapped in a PrimeNG `p-iconfield` component
- **AND** a `p-inputicon` with a lock icon (e.g., `pi pi-lock`) is displayed at the start of the field

### Requirement: PrimeNG Button for form submission
The submit button SHALL use a PrimeNG `pButton` component instead of Bootstrap `btn`.

#### Scenario: Submit button uses PrimeNG Button
- **WHEN** the login page is displayed
- **THEN** the submit button is rendered as a PrimeNG `p-button` component
- **AND** the button label uses the `login.form.button` i18n key
- **AND** the `data-cy="submit"` attribute is preserved
- **AND** the button triggers the `login()` method on click

### Requirement: PrimeNG Checkbox for remember me
The "remember me" option SHALL use a PrimeNG `p-checkbox` component.

#### Scenario: Remember me uses PrimeNG Checkbox
- **WHEN** the login page is displayed
- **THEN** the remember me option is rendered as a PrimeNG `p-checkbox` component with a PrimeNG label
- **AND** the checkbox uses two-way binding with `loginForm.rememberMe`
- **AND** the label uses the `login.form.rememberme` i18n key

### Requirement: PrimeNG Message for authentication error
Authentication errors SHALL be displayed using a PrimeNG `p-message` component instead of Bootstrap `alert alert-danger`.

#### Scenario: Authentication error displayed with PrimeNG Message
- **WHEN** authentication fails
- **THEN** a PrimeNG `p-message` component displays the error
- **AND** the error uses severity `error`
- **AND** the error text uses the `login.messages.error.authentication` i18n key
- **AND** the `data-cy="loginError"` attribute is preserved

### Requirement: Forgot password and register links preserved
The login screen SHALL continue to display links for "forgot password" and "register" below the login form.

#### Scenario: Forgot password link is present
- **WHEN** the login page is displayed
- **THEN** a link to `/account/reset/request` is displayed
- **AND** the link uses the `login.password.forgot` i18n key
- **AND** the `data-cy="forgetYourPasswordSelector"` attribute is preserved

#### Scenario: Register link is present
- **WHEN** the login page is displayed
- **THEN** a link to `/account/register` is displayed
- **AND** the link text uses the `global.messages.info.register.noaccount` and `global.messages.info.register.link` i18n keys

### Requirement: No Bootstrap classes on login form elements
The login form SHALL NOT use Bootstrap form utility classes (`form-control`, `btn`, `btn-primary`, `form-check`, `form-check-input`, `form-check-label`, `alert`, `alert-danger`, `alert-warning`) on its form elements.

#### Scenario: Login form uses PrimeNG styling exclusively
- **WHEN** the login page is displayed
- **THEN** no Bootstrap `form-control`, `btn`, or `alert` classes appear on login form inputs, buttons, or messages
