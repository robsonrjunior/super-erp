## Why

Currently, unauthenticated users landing on the app see a welcome/home page displaying a "You are not logged in" message with links to login and register. This adds an unnecessary intermediate step — users must click through to reach the login form. Redirecting unauthenticated users straight to `/login` streamlines the experience and avoids exposing a page with no useful content to users who haven't authenticated.

## What Changes

- Add an auth guard on the empty-path (`''`) home route so unauthenticated users are redirected to `/login` instead of seeing the home page
- The home page remains the landing page for authenticated users after login
- No backend changes required — routing-only frontend change

## Capabilities

### New Capabilities

- `login-redirect`: Redirects unauthenticated users from the home page directly to the login screen

### Modified Capabilities

<!-- None — no existing spec covers routing/auth behavior -->

## Impact

- **Frontend**: `src/main/webapp/app/app.routes.ts` — add `canActivate: [UserRouteAccessService]` to the home route
- **Test impact**: Existing home component tests may need adjustment if they assume the home page loads without auth
