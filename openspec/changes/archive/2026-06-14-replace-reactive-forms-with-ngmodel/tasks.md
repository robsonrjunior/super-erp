## 1. Shared Utility

- [x] 1.1 Convert `data-util.service.ts` `loadFileToForm` to `loadFileToModel` accepting plain object instead of `FormGroup`

## 2. Reference Entity Forms (Country, State, City)

- [x] 2.1 Convert `country-update` component and template to `[(ngModel)]`
- [x] 2.2 Convert `state-update` component and template to `[(ngModel)]`
- [x] 2.3 Convert `city-update` component and template to `[(ngModel)]`

## 3. Core Business Entity Forms

- [x] 3.1 Convert `tenant-update` component and template to `[(ngModel)]`
- [x] 3.2 Convert `supplier-update` component and template to `[(ngModel)]`
- [x] 3.3 Convert `customer-update` component and template to `[(ngModel)]`
- [x] 3.4 Convert `person-update` component and template to `[(ngModel)]`
- [x] 3.5 Convert `company-update` component and template to `[(ngModel)]`
- [x] 3.6 Convert `product-update` component and template to `[(ngModel)]`
- [x] 3.7 Convert `raw-material-update` component and template to `[(ngModel)]`
- [x] 3.8 Convert `warehouse-update` component and template to `[(ngModel)]`
- [x] 3.9 Convert `stock-movement-update` component and template to `[(ngModel)]`
- [x] 3.10 Convert `sale-update` component and template to `[(ngModel)]`
- [x] 3.11 Convert `sale-item-update` component and template to `[(ngModel)]`

## 4. Admin Entity Forms

- [x] 4.1 Convert `authority-update` component and template to `[(ngModel)]` (Authority has no form service — created inline)
- [x] 4.2 Convert `user-management-update` component and template to `[(ngModel)]`

## 5. Account Forms

- [x] 5.1 Convert `login` component and template to `[(ngModel)]`
- [x] 5.2 Convert `password` (change password) component and template to `[(ngModel)]`
- [x] 5.3 Convert `register` component and template to `[(ngModel)]`
- [x] 5.4 Convert `settings` component and template to `[(ngModel)]`
- [x] 5.5 Convert `password-reset-init` component and template to `[(ngModel)]`
- [x] 5.6 Convert `password-reset-finish` component and template to `[(ngModel)]`

## 6. Remove Form Services

- [x] 6.1 Delete all 15 `XxxFormService` files from entity `update/` directories

## 7. Verify & Clean Up

- [x] 7.1 Run ESLint and fix any issues
- [x] 7.2 Run Prettier formatting
- [ ] 7.3 Run frontend tests (`./npmw test`) and fix any failures
- [x] 7.4 Verify `ReactiveFormsModule` is removed from all component `imports` arrays
- [x] 7.5 Verify no remaining `FormGroup`, `FormControl`, `FormBuilder` imports in converted components
