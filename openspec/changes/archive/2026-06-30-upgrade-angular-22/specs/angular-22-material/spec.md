## ADDED Requirements

### Requirement: Angular Material 22 and CDK 22
The project SHALL use Angular Material 22.x and Angular CDK 22.x stable (`@angular/material` and `@angular/cdk`).

#### Scenario: Material and CDK resolve to 22.x
- **WHEN** `npm ls @angular/material @angular/cdk` is executed
- **THEN** both packages SHALL resolve to `^22.0.0` or later 22.x stable

#### Scenario: Material components render correctly
- **WHEN** the application is loaded in a browser
- **THEN** all Angular Material components used in the application SHALL render without visual regressions

### Requirement: Material theme compatibility
The existing Material theme configuration SHALL remain functional after the upgrade, or SHALL be updated to the Angular 22 theming API if breaking changes require migration.

#### Scenario: Theme applies correctly
- **WHEN** the application loads
- **THEN** the Material theme (including color, typography, and density) SHALL apply correctly to all Material components
