## 1. Routing change

- [x] 1.1 Add `canActivate: [UserRouteAccessService]` to the home route (`path: ''`) in `app.routes.ts`

## 2. Verification

- [x] 2.1 Run `./npmw run lint` to verify no ESLint errors
- [x] 2.2 Run `./npmw test` to verify no test regressions (no spec files exist in the project — pre-existing)
- [x] 2.3 Manually verify: unauthenticated user at `/` is redirected to `/login`
- [x] 2.4 Manually verify: authenticated user at `/` sees the home page
