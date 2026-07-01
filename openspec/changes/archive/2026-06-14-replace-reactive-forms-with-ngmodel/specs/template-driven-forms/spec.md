## ADDED Requirements

### Requirement: Entity forms use template-driven ngModel binding
All entity update forms (create and edit) SHALL use Angular template-driven forms with `[(ngModel)]` binding instead of Reactive Forms (`FormGroup`/`FormControl`).

#### Scenario: Form renders with empty model for new entity
- **WHEN** user navigates to an entity creation route without route data
- **THEN** the form initializes with default/empty model values bound via `[(ngModel)]`
- **THEN** all input fields are empty or show placeholder values

#### Scenario: Form renders with populated model for existing entity
- **WHEN** user navigates to an entity edit route with existing entity data from the route resolver
- **THEN** the form populates all input fields with the entity's current values via `[(ngModel)]` binding

#### Scenario: Form submission creates new entity
- **WHEN** user fills all required fields and clicks submit on a create form
- **THEN** the component calls the entity service's `create()` method with the model object as payload
- **THEN** `isSaving` signal is set to `true` during the request and `false` after completion

#### Scenario: Form submission updates existing entity
- **WHEN** user modifies fields and clicks submit on an edit form
- **THEN** the component calls the entity service's `update()` method with the model object as payload
- **THEN** `isSaving` signal is set to `true` during the request and `false` after completion

### Requirement: HTML5 validation replaces imperative validators
Form field validation SHALL use native HTML5 validation attributes (`required`, `minlength`, `maxlength`, `email`, `pattern`) on input elements instead of TypeScript `Validators.*` functions.

#### Scenario: Required field shows error when empty and touched
- **WHEN** user focuses and then blurs a required field without entering a value
- **THEN** the field reference (`#fieldRef="ngModel"`) reports `fieldRef.invalid === true` and `fieldRef.errors.required === true`
- **THEN** a validation error message is displayed below the field

#### Scenario: Minlength constraint triggers validation error
- **WHEN** user enters fewer characters than the `minlength` attribute specifies and the field is touched
- **THEN** the field reference reports validation error with `minlength` constraint

#### Scenario: Maxlength constraint triggers validation error
- **WHEN** user enters more characters than the `maxlength` attribute specifies
- **THEN** the field reference reports validation error with `maxlength` constraint

#### Scenario: Valid form enables submit button
- **WHEN** all required fields are valid and meet all constraints
- **THEN** the submit button is enabled (`[disabled]="editForm.invalid"` evaluates to `false`)

### Requirement: Form services are removed
All 15 entity form services (`XxxFormService`) SHALL be deleted. Form initialization, value extraction, and reset logic SHALL reside directly in the component class.

#### Scenario: Component creates entity without form service
- **WHEN** an entity update component needs to extract values for submission
- **THEN** the component accesses model properties directly from the bound object (no `formService.getXxx(form)` call)

#### Scenario: Component resets form without form service
- **WHEN** an entity update component needs to reset after a successful save
- **THEN** the component reassigns default model values directly (no `formService.resetForm(form, entity)` call)

### Requirement: Account and login forms use template-driven binding
Account management forms (password, register, settings, password-reset) and the login form SHALL use `[(ngModel)]` binding instead of inline `FormGroup` instances.

#### Scenario: Login form submits credentials via ngModel
- **WHEN** user enters username and password and clicks login
- **THEN** the login method reads `username` and `password` from the bound component properties and calls the auth service

#### Scenario: Settings form patches account data
- **WHEN** account data loads from the API
- **THEN** the component assigns the account object to a property, which propagates to form inputs via `[(ngModel)]`

#### Scenario: Password form validates confirmation match
- **WHEN** user enters non-matching passwords in new password and confirm password fields
- **THEN** the form shows a "passwords do not match" error message

### Requirement: File upload utility works without FormGroup
The `data-util.service.ts` `loadFileToForm` method SHALL be replaced with a method that accepts a plain model object instead of a `FormGroup` parameter.

#### Scenario: File selection updates model property
- **WHEN** user selects an image file via a file input
- **THEN** the utility reads the file as a base64 data URL and assigns it to the specified property on the model object
