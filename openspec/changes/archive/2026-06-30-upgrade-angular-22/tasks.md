## 1. Pre-upgrade baseline

- [x] 1.1 Commit current state with a meaningful message
- [x] 1.2 Run `./npmw test` and ensure all frontend tests pass
- [x] 1.3 Run `./npmw run lint` and ensure zero lint violations
- [x] 1.4 Run `./npmw run start` and verify the application loads without errors

## 2. Angular core and CLI upgrade

- [x] 2.1 Run `npx ng update @angular/core@22 @angular/cli@22` to upgrade core packages and CLI
- [x] 2.2 Review and accept migration schematics for `angular.json` and `tsconfig*.json` changes
- [x] 2.3 Update TypeScript version as required by Angular 22 peer dependencies
- [x] 2.4 Run `./npmw install` to install updated dependency tree

## 3. Angular Material and CDK upgrade

- [x] 3.1 Run `npx ng update @angular/material@22 @angular/cdk@22` to upgrade Material packages
- [x] 3.2 Review and accept any Material theming migration schematics
- [x] 3.3 Run `./npmw install` to install updated dependency tree

## 4. Angular ESLint alignment

- [x] 4.1 Run `npx ng update @angular-eslint/schematics@22` to upgrade ESLint packages
- [x] 4.2 Run `./npmw install` to install updated dependency tree

## 5. Third-party compatibility verification

- [x] 5.1 Check PrimeNG version compatibility with Angular 22; upgrade if necessary
- [x] 5.2 Check ng-bootstrap version compatibility with Angular 22; upgrade if necessary
- [x] 5.3 Check ngx-translate version compatibility with Angular 22
- [x] 5.4 Run `./npmw install` if any third-party packages were upgraded

## 6. Source code fixes

- [x] 6.1 Search codebase for deprecated Angular 21 APIs removed in v22 and fix any occurrences
- [x] 6.2 Verify `jhipster-needle-*` markers are preserved in all generated files after schematic migrations
- [x] 6.3 Run `./npmw run start` and fix any compilation errors

## 7. Verification

- [x] 7.1 Run `./npmw run lint` and fix any new ESLint violations
- [x] 7.2 Run `./npmw test` and ensure all frontend tests pass
- [x] 7.3 Run `./npmw run start` and manually verify key application features (login, navigation, i18n)
- [x] 7.4 Run production build (`./gradlew -Pprod clean bootJar`) and verify success
- [x] 7.5 Verify service worker registration in production build
- [x] 7.6 Run `./gradlew test integrationTest -x webapp -x webapp_test` to confirm backend is unaffected
