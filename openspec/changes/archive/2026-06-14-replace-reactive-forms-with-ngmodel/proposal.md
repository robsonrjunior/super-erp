## Why

The codebase uses Angular Reactive Forms (`FormGroup`/`FormControl`/`FormBuilder`) across 22+ components and 15 dedicated form services, adding complexity, boilerplate, and an extra layer of abstraction (form services) that is unnecessary for the system's form needs. Migrating to template-driven forms with `[(ngModel)]` simplifies the code, reduces maintenance burden, and aligns with the Angular team's recommendation for simpler form scenarios — which is exactly this system's case (straightforward CRUD forms with no dynamic form arrays, complex cross-field validation, or custom async validators).

## What Changes

- **BREAKING**: Remove 15 entity form services (`XxxFormService`) — form logic moves into components and templates
- **BREAKING**: Replace `ReactiveFormsModule` with `FormsModule` in all entity-update, account, and login components
- **BREAKING**: Replace `FormGroup`/`FormControl`/`FormBuilder` with plain TypeScript model objects bound via `[(ngModel)]`
- Replace `[formGroup]`/`formControlName` bindings with `[(ngModel)]` + local template reference variables (`#field="ngModel"`)
- Move validation from TypeScript `Validators.required`/`.minLength()`/`.maxLength()`/`.email()` to HTML template attributes (`required`, `minlength`, `maxlength`, `email`)
- Replace `editForm.invalid` checks with Angular `ngForm`-based validity checks
- Keep `isSaving` signal and `ngSubmit` save patterns — only the form mechanism changes
- Keep delete dialogs as-is (they already use template-driven approach)
- Keep admin components (configuration, logs) and footer as-is (they already use `ngModel` with signals)
- Update `data-util.service.ts` — remove `loadFileToForm` dependency on `FormGroup` parameter

### Non-goals

- No backend changes whatsoever
- No changes to delete dialog components (already template-driven)
- No changes to admin filter inputs (already using `ngModel` with signals)
- No changes to form validation rules — same constraints, different declaration syntax
- No changes to `jhipster-needle-*` markers — they stay intact

## Capabilities

### New Capabilities

- `template-driven-forms`: Standardized pattern for template-driven forms using `[(ngModel)]` across all CRUD screens, replacing Reactive Forms. Covers form binding, validation, submission, and entity population.

### Modified Capabilities

<!-- No existing specs affected. Current openspec/specs/ has entity-method-conventions, soft-deletable-entity, and test-skill — none relate to form implementation. -->

## Impact

- **Affected code**: 22 TypeScript components (16 entity-update + 5 account + 1 login), 15 form service files (to be deleted), ~38 HTML templates, 1 utility service (`data-util.service.ts`)
- **Dependencies**: Remove `ReactiveFormsModule` imports, add `FormsModule` imports where needed
- **No API changes**: Backend remains untouched
- **No new dependencies**: `FormsModule` is already part of `@angular/forms`
- **Risk**: JHipster's entity generator could re-introduce Reactive Forms on future regeneration — relies on developer discipline or ESLint rules to prevent regression
