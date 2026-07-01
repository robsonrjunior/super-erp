## 1. Hide navbar on login route

- [x] 1.1 Add a `Router` injection and a computed signal `isLoginRoute` in `main.ts` that checks if the current route is `/login`
- [x] 1.2 Wrap the `<router-outlet name="navbar">` in `main.html` with `@if (!isLoginRoute())` to conditionally hide the navbar
- [x] 1.3 Verify the page ribbon (`<jhi-page-ribbon />`) and footer remain visible on the login page

## 2. Import PrimeNG modules in login component

- [x] 2.1 Add `InputTextModule`, `ButtonModule`, `CheckboxModule`, `IconFieldModule`, `InputIconModule`, and `MessageModule` to the `imports` array in `login.ts`
- [x] 2.2 Remove `FormsModule` from imports if no longer needed (PrimeNG InputText works with standard `ngModel` from `@angular/forms`)

## 3. Refactor login template to PrimeNG components

- [x] 3.1 Replace the outer Bootstrap `div.d-flex.justify-content-center > div.col-*` wrapper with a PrimeNG `<p-card>` component
- [x] 3.2 Replace the username `<input class="form-control">` with a `<p-iconfield>` containing a `<p-inputicon>` (user icon) and an `<input pInputText>` preserving `[(ngModel)]`, `name`, `id`, and `data-cy`
- [x] 3.3 Replace the password `<input class="form-control">` with a `<p-iconfield>` containing a `<p-inputicon>` (lock icon) and an `<input pInputText type="password">` preserving bindings and attributes
- [x] 3.4 Replace the Bootstrap `<button class="btn btn-primary">` with a `<p-button>` component, preserving `type="submit"`, `data-cy="submit"`, and the i18n label
- [x] 3.5 Replace the `<input class="form-check-input" type="checkbox">` with a `<p-checkbox>` component, preserving `[(ngModel)]`, `name`, `id`, and adding a `<label>` with the `login.form.rememberme` i18n key
- [x] 3.6 Replace the `<div class="alert alert-danger">` authentication error block with a `<p-message severity="error">` component, preserving `data-cy="loginError"` and i18n text
- [x] 3.7 Keep the forgot password and register links, wrapping them in simple `<div>` elements (no Bootstrap `alert-warning` classes)
- [x] 3.8 Preserve the `<h1 data-cy="loginTitle">` heading with its i18n directive

## 4. Login component styles

- [x] 4.1 Create `login.scss` with styles to center the login card on the page (flexbox centering, max-width)
- [x] 4.2 Add `styleUrl: './login.scss'` to the `@Component` decorator in `login.ts`
- [x] 4.3 Remove any remaining Bootstrap utility classes from the template that are no longer needed

## 5. Verification

- [x] 5.1 Run `./npmw run lint` and fix any ESLint errors
- [ ] 5.2 Run `./npmw run start` and manually verify the login page renders correctly with PrimeNG components
- [ ] 5.3 Verify the navbar is hidden on `/login` but visible on other routes (e.g., home after login)
- [x] 5.4 Verify all `data-cy` attributes are preserved for e2e tests
- [ ] 5.5 Verify authentication still works (successful login redirects to home, failed login shows error message)
- [ ] 5.6 Verify i18n translation works correctly (test with Portuguese and English)
