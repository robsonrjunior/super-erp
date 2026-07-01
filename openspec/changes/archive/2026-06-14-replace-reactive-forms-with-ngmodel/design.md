## Context

The frontend uses Angular Reactive Forms (`FormGroup`/`FormControl`) across 22+ components. Entity CRUD forms follow a JHipster-generated pattern with 15 dedicated form services (`XxxFormService`) that create typed `FormGroup` instances, extract raw values, and reset forms. Account/login forms create `FormGroup` directly in components. Validation is imperative TypeScript (`Validators.required`, `.minLength()`, `.maxLength()`, `.email()`). The `id` field is always disabled with `{ disabled: true }` in entity forms, requiring `getRawValue()` for extraction.

Template-driven forms have been partially adopted: 16 delete dialogs already use `(ngSubmit)` without reactive forms; 3 admin components use `ngModel` with signals for filter inputs. This migration completes the pattern across the entire frontend.

## Goals / Non-Goals

**Goals:**
- Replace all `FormGroup`/`FormControl`/`FormBuilder` usage with template-driven `[(ngModel)]` binding
- Remove 15 entity form services — form logic moves into components and templates
- Migrate validation from TypeScript `Validators.*` to HTML5 validation attributes (`required`, `minlength`, `maxlength`, `email`)
- Maintain identical form behavior (validation rules, submit flow, error display, loading states)
- Keep `isSaving` Angular signal pattern and `ngSubmit` submission flow unchanged
- Remove `ReactiveFormsModule` from all component `imports` arrays

**Non-Goals:**
- No changes to backend code, API contracts, or data models
- No changes to delete dialogs (already template-driven)
- No changes to admin filter inputs (already `ngModel`-based)
- No changes to form validation rules — same constraints, different declaration syntax
- No changes to `jhipster-needle-*` markers
- No new i18n keys needed

## Decisions

### Decision 1: Pure `ngModel` over `ngForm` form reference

**Chosen**: Bind data properties with `[(ngModel)]` on each input. Track form validity via local template references (`#name="ngModel"`) for per-field error messages. Submit button disabled via `#editForm="ngForm"` reference checking `editForm.invalid`.

**Alternative**: Keep a `FormGroup` (template-driven version) using `ngForm`. Rejected because it still requires linking `ngModel` names to form control names, adding indirection with no benefit for simple CRUD forms.

**Rationale**: The system's forms are straightforward — each field maps 1:1 to an entity property with standard validation. No dynamic form arrays, no cross-field validators, no custom async validators. Template-driven with direct model binding is the simplest approach.

### Decision 2: Drop form services entirely

**Chosen**: Remove all 15 `XxxFormService` files. Move form initialization (setting defaults, extracting values) into the component class.

**Alternative**: Keep services but refactor to return typed interfaces instead of `FormGroup`. Rejected — adds a file for a single function call; the component already has all the context needed.

**Rationale**: Without `FormGroup`, the form services had two responsibilities: (a) create typed groups, (b) extract values and reset. Both become trivial one-liners in the component (e.g., `this.country = { ...DEFAULT_COUNTRY }` and `const payload = this.country`).

### Decision 3: Validation via HTML5 attributes

**Chosen**: Use HTML `required`, `minlength`, `maxlength`, `email` attributes directly on `<input>` elements. Use `#field="ngModel"` template variables for per-field error display.

**Alternative**: Custom validators or a validation directive. Rejected — adds unnecessary complexity.

**Rationale**: All current validators map directly to HTML5 attributes:
- `Validators.required` → `required`
- `Validators.minLength(n)` → `[minlength]="n"` or `minlength="n"`
- `Validators.maxLength(n)` → `[maxlength]="n"` or `maxlength="n"`
- `Validators.email` → `email`
- `Validators.pattern(expr)` → `[pattern]="expr"`

### Decision 4: Entity `id` field handling

**Chosen**: Bind `id` to a hidden input or keep it as a read-only property on the model object. The `id` was `disabled: true` in Reactive Forms to prevent editing. With template-driven, set the input as `[readonly]="true"` or `disabled` (hidden input won't need submission changes).

**Alternative**: Use `[disabled]="true"` — but disabled inputs don't submit their values in HTML forms. Rejected for the `id` field since it's needed in the payload.

**Rationale**: Since `id` is only needed for the API call (update vs create), it belongs on the model object directly, not in a disabled form field. The component's `save()` method can check `this.entity.id` for the same logic.

### Decision 5: File upload (data-util.service.ts)

**Chosen**: Remove `loadFileToForm(event, editForm: FormGroup, field, isImage)` method. Replace with a method that reads the file directly into the entity model object: `loadFileToModel(event, model, field, isImage): void`.

**Rationale**: Without `FormGroup`, the utility needs to patch an object property instead of calling `form.patchValue()`. Same `FileReader` logic, different target.

### Decision 6: Component template pattern

**Chosen**: Consistent pattern across all entity-update components:
```html
<form name="editForm" #editForm="ngForm" (ngSubmit)="save()" novalidate>
  <input type="text" name="name" [(ngModel)]="entity.name" required minlength="2" maxlength="100" #nameRef="ngModel" />
  @if (nameRef.invalid && (nameRef.dirty || nameRef.touched)) {
    <small class="form-text text-danger">...</small>
  }
  <button type="submit" [disabled]="editForm.invalid || isSaving()">Salvar</button>
</form>
```

## Risks / Trade-offs

- **[Risk] JHipster regeneration could reintroduce Reactive Forms** → **Mitigation**: Document the convention in AGENTS.md. ESLint rules or custom JHipster blueprint would prevent regression, but that's outside current scope.

- **[Risk] No `FormGroup.valueChanges` observable for reactive patterns** → **Mitigation**: No component currently uses `valueChanges` subscriptions. Not applicable.

- **[Risk] Form reset after save loses simplicity without `form.reset()`** → **Mitigation**: Re-initialize the model object with defaults (same approach as setting `initialValue`).

- **[Risk] Two-way binding on complex object graphs (e.g., `entity.company.name`) needs nested property paths** → **Mitigation**: `[(ngModel)]` supports nested paths natively (`[(ngModel)]="entity.company.id"`). Use `<select>` with `[ngModel]` for relationship dropdowns, matching existing `<select>` patterns already in templates.

- **[Trade-off] Losing type-safe `FormGroup` interfaces** → Acceptable. Entity interfaces (`ICountry`, `IPerson`, etc.) already provide type safety for the model objects.

- **[Trade-off] HTML5 validation is less flexible than custom validators** → Acceptable. Current validators are all standard HTML5-compatible. No custom or cross-field validators exist.

## Migration Plan

1. Convert entity-update components one entity at a time (15 entities), each in its own commit
2. Convert account components (5 components)
3. Convert login component
4. Convert `data-util.service.ts` file upload utility
5. Delete all 15 form service files
6. Run ESLint, Prettier, and Vitest tests after each batch
7. Manual smoke test: create/edit each entity to verify form submission works

**Rollback**: Each entity conversion is independent. Revert individual commits if issues arise. No database or API changes to worry about.

## Open Questions

None — the current form patterns are well understood and the migration is mechanical.
