## Why

The navbar currently uses ng-bootstrap dropdowns and Bootstrap utility classes, which are inconsistent with the PrimeNG-based UI adopted for the login screen and footer. Additionally, dark mode links render in green (PrimeNG Aura's default emerald primary palette), which clashes visually with the ERP's professional aesthetic. Removing the redundant language section from the navbar (already present in the footer) and adding a search input sets the stage for global search functionality.

## What Changes

- Replace ng-bootstrap dropdowns in the navbar with PrimeNG components (`p-tieredMenu`, `p-menubar` or equivalent)
- Remove green link color in dark mode by replacing `var(--p-primary-300)` / `var(--p-primary-200)` with high-contrast neutral colors
- Remove the language dropdown section from the navbar (language switching remains available in the footer)
- Add a centered search input field (`p-iconfield` + `p-inputtext`) in the navbar, between the logo and the menu items — placeholder only, no search logic wired
- Update navbar SCSS to use PrimeNG CSS variables consistently

## Capabilities

### New Capabilities

- `primeng-navbar`: Navbar refactored with PrimeNG components (menubar, icon field, input text), replacing ng-bootstrap dropdowns. Includes a centered search input placeholder.

### Modified Capabilities

- `dark-mode-toggle`: Dark mode link color requirement changed from green primary palette (`var(--p-primary-300)` / `var(--p-primary-200)`) to high-contrast neutral link colors.

## Impact

- **Navbar component** (`src/main/webapp/app/layouts/navbar/`): template, TypeScript, and SCSS rewritten to use PrimeNG
- **Global styles** (`src/main/webapp/content/scss/global.scss`): `.dark-mode a` link colors changed
- **Dark mode toggle spec** (`openspec/specs/dark-mode-toggle/spec.md`): delta spec for link color requirement
- **Navbar dependencies**: `NgbCollapse`, `NgbDropdown`, `NgbDropdownMenu`, `NgbDropdownToggle` imports removed; PrimeNG `MenubarModule`, `IconFieldModule`, `InputIconModule`, `InputTextModule` added
- `ActiveMenuDirective` and `FindLanguageFromKeyPipe` no longer needed in navbar (removed language section)
